var currentHover = null;
var zoomTimeout = null;
var url = 'http://localhost:9998/';
var minHeight = 300;
var screenWidth = $(window).width();
var screenHeight = $(window).height();
var screenResizeTimeout, treeRedrawTimeout;
var zoomWidth = 100;
var minimapNodes = {}, cachedZoomNodes, maxMinimapSize;
var currentMousePos = {x: -1, y: -1};
var zoomLeft = 0;
var zoomRight = 0;
var zoomHeight = 0;
var minimapHeight = 0;
var zoomNodeLocations = [];
var currentHoverNode = null;
var dragFrom = null;
var mutations = ["SNP", "INDEL"];
var mutColors = ["0000FF", "00FF00", "FF0000"];
var minY = 0;
var maxY = 0;

/**
 * When the screen resizes, or one of the panels resizes, the others need to be resized as well
 * This function will update all other panels when one resizes, or update all panels when the screen resizes.
 */
function screenResize() {
    //If the width has changed since last time, update the width of the left sub panel accordingly
    if ($(window).width() != screenWidth) {
        $("#phylogenyContainer").width(Math.ceil($("#phylogenyContainer").width() * $(window).width() / screenWidth));
        screenWidth = $(window).width();
    }

    //If the height has changed since last time, update the height of the zoom panel accordingly
    if ($(window).height() != screenHeight) {
        $("#zoom").height(Math.ceil($("#zoom").height() * $(window).height() / screenHeight));
        screenHeight = $(window).height();
    }

    if ($("#zoom").height() + $("#header").height() > (screenHeight - 25)) {
        $("#zoom").height(screenHeight - 25 - $("#header").height());
    }

    //Update the height of the sub panel after change of the zoom panel.
    //The sub panel becomes the height of the screen, minus the height of the zoom and header bar.
    var borderHeight = parseInt($("#zoom").css("border-bottom-width").replace('px', ''));
    $('#sub').height($(window).height() - $("#zoom").height() - $("#header").height() - borderHeight);
    $('#sub').height($(window).height() - $("#zoom").height() - $("#header").height() - borderHeight);
    $('#minimap').height($('#sub').height());
    $('#minimap .slider').css('height', '100%');
    if ($('#zoom').find('canvas').length) { //Update the canvas height and width in the zoom panel
        $('#zoom').find('canvas')[0].height = $('#zoomWindow').height();
        $('#zoom').find('canvas')[0].width = $('#zoomWindow').width();
        updatezoomWindow();
    }

    //Update the width of the sub panel and zoom panel and the canvasses in it.
    //If the width of a subpanel has changed or the screen, update both the upper canvas and the panels in the sub.
    //Both canvasses need to be updated as well.
    var borderWidth = parseInt($("#phylogenyContainer").css("border-right-width").replace('px', ''));
    $('#minimapContainer').width($("#sub").width() - $("#phylogenyContainer").width() - borderWidth - 2);
    if ($('#minimapContainer').find('canvas').length) {
        $('#minimap').find('canvas')[0].width = $('#minimap').width();
        $('#minimap').find('canvas')[0].height = $('#minimap').height();
        drawMinimap(null); //Update the canvas
        zoom(1, 0, 1);
        if (treeRedrawTimeout) {
            clearTimeout(treeRedrawTimeout);
        }
        treeRedrawTimeout = setTimeout(function () {
            resizePhyloTree();
        }, 500);
    }

    if ($('#phyloButtons').width() < 180) {
        $('#compGenomesButton').hide();
    } else {
        $('#compGenomesButton').show();
    }
}

$('document').ready(function () {

    screenResize();

    //Add a resizable to the south side of the zoom panel
    $("#zoom").resizable({
        handles: 's'
    });
    //Add a resizable to the east side of the phylogeny panel
    $("#phylogenyContainer").resizable({
        handles: 'e'
    });

    //Trigger screenResize when a resizable panel is resized
    $('#zoom, #phylogenyContainer').resize(function () {
        if (screenResizeTimeout) {
            clearTimeout(screenResizeTimeout);
        }
        screenResizeTimeout = setTimeout(function () {
            screenResize();
        }, 500);
    });

    //Trigger screenResize after a real screen resize
    $(window).resize(function () {
        if (screenResizeTimeout) {
            clearTimeout(screenResizeTimeout);
        }
        screenResizeTimeout = setTimeout(function () {
            screenResize();
        }, 500);
    });

    //Bind the mouseScroll to trigger zooming on the miniMap
    $('body').bind('mousewheel DOMMouseScroll', function (e) {
        if ($(currentHover).is('#minimap')) {
            e.preventDefault();
            zoom(e.originalEvent.wheelDelta, 1, Math.floor(currentMousePos.x - $(currentHover).position().left));
        } else if ($(currentHover).is('#zoomWindow')) {
            var ratio = $('#minimap .slider').width() / $('#zoom').width();
            e.preventDefault();
            var left = (currentMousePos.x - $(currentHover).position().left) * ratio;
            zoom(e.originalEvent.wheelDelta, 1, pxToInt($('#minimap .slider').css('left')) + left);
            updateZoomValues();
            drawZoom(null);
        }
    });

    //Add the slider
    var slider = $('#minimap').find('.slider');
    $(slider)
        .draggable({
            containment: "parent",
            axis: 'x',
            stop: function () {
                //Temp, to make it look smooth, can be done if lazy loading is implemented
                //updatezoomWindow();

                //Should be this when we have specific zoomData
                clearTimeout(zoomTimeout);
                zoomTimeout = setTimeout(function () {
                    updatezoomWindow()
                }, 50);
            }
        });

    //This makes the system know if the mouse is currently over a genome.
    $('.genome').hover(function () {
        currentHover = this;
    }, function () {
        currentHover = null;
    });

    //Constantly check where the mouse is, to let the zooming work better.
    $('#minimapContainer').mousemove(function (event) {
        currentMousePos.x = event.pageX;
        currentMousePos.y = event.pageY;
    });

    //Map the location of the mouse within the zoom window, if hovering over a node, show it's labeldata.
    $('#zoom').mousemove(function (event) {
        var that = this;
        currentMousePos.x = event.pageX;
        currentMousePos.y = event.pageY;
        var x = currentMousePos.x - $(this).position().left;
        var y = currentMousePos.y - $(this).position().top;
        var found = false;

        if (dragFrom != null) {
            var d = new Date();
            var time = d.getMilliseconds();
            var diff = dragFrom - currentMousePos.x;
            dragFrom = currentMousePos.x;
            var ratio = $('#minimap .slider').width() / $('#zoom').width();
            var left = diff * ratio;
            var maxLeft = $('#minimap').width() - $('#minimap .slider').width();
            var newLeft = Math.min(maxLeft, Math.max(0, pxToInt($('#minimap .slider').css('left')) + left));
            $('#minimap .slider').css('left', newLeft +'px');
            updateZoomValues();
            drawZoom(null);
            clearTimeout(zoomTimeout);
            zoomTimeout = setTimeout(function () {
                updatezoomWindow();
            }, 500);
        } else {
            $.each(zoomNodeLocations, function (key, node) {
                if (node.x + 5 > x && node.x - 5 < x && node.y + 5 > y && node.y - 5 < y) {
                    found = true;
                    if (node.id != currentHoverNode) {
                        currentHoverNode = node.id;
                        var dialog = $('#nodeDialog');
                        var annotations = "<ul>";
                        $.each(node.annotations, function(key, value) {
                            annotations += "<li>"+ value +"</li>";
                        });
                        annotations += "</ul>";
                        dialog.find('.message').html(node.label);
                        dialog.find('.annotation').html(annotations);
                    }
                    return false;
                }
            });
            if (!found) {
                currentHoverNode = -1;
            }
        }
    });

    $('#toggleButtons').click(function () {
        $('body').toggleClass('showButtons');
    });

    $('#zoomIn').click(function () {
        var slider = $('#minimap .slider');
        var center = Math.floor(pxToInt(slider.css('left')) + slider.width() / 2);
        zoom(1, 5, center);
    });

    $('#zoomOut').click(function () {
        var slider = $('#minimap .slider');
        var center = Math.floor(pxToInt(slider.css('left')) + slider.width() / 2);
        zoom(-1, 5, center);
    });

    $('#zoom').mousedown(function() {
        dragFrom = currentMousePos.x;
    });

    $('body').mouseup(function() {
        dragFrom = null;
    });

    $('#coordinateSelector').keyup(function (e) {
        var code = e.keyCode || e.which;
        if (code == 13) {
            e.preventDefault();
            goToX($(this).val(), 100);
        }
    });

    $("#searchMutation").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: url + 'api/search',
                dataType: "JSON",
                type: 'GET',
                data: {
                    searchString: request.term,
                    searchType: 'GenomicFeatureSearch'
                },
                success: function(result) {
                    var data = [];
                    $.each(result.gFeatureSearchMatches, function(key, value) {
                        data.push({
                            label: value.feature.displayName,
                            value: value.strands[0].x +"-"+ value.strands[value.strands.length - 1].x
                        });
                    });
                    response( data );
                }
            });
        },
        minLength: 3,
        select: function( event, ui ) {
            var coords = ui.item.value.split('-');
            var zoom = Math.min(1000, Math.ceil(maxMinimapSize / (parseInt(coords[1]) - parseInt(coords[0]))));
            goToX(coords[0], zoom);
            $("#coordinateSelector").val(coords[0]);
            setTimeout(function() { $('#searchMutation').val(""); }, 500);
        },
        open: function() {
            $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
        },
        close: function() {
            $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
        }
    });

    $('#mutationLegenda').hover(function () {
        $('#legendaCanvas').show();
    }, function () {
        $('#legendaCanvas').hide();
    });

    initialize();
});

/**
 * Change css pixel distance to an integer: 25px to 25
 * @param css The css value
 * @returns {Number} The css value changed to an integer
 */
function pxToInt(css) {
    return parseFloat(css.replace('px', ''));
}

/**
 * Draw the zoom data
 * @param nodes
 */
var drawZoom = function (nodes) {
    if (nodes == null) {
        nodes = cachedZoomNodes;
    } else {
        cachedZoomNodes = nodes;
        zoomHeight = calcHeight(nodes);
    }
    if (typeof nodes == 'undefined') {
        return;
    }
    var ratio = $('#zoomWindow').width() / (zoomRight - zoomLeft);
    if (Object.keys(nodes).length > 0) {
        var canvas = $('#zoomWindow canvas')[0];
        draw(nodes, canvas, true, canvas.height / zoomHeight, function (x) {
            return (x - zoomLeft) * ratio;
        });
        drawScale(canvas);
    } else {
        var c = $('#zoomWindow canvas')[0];
        var ctx = c.getContext("2d");
        ctx.clearRect(0, 0, c.width, c.height);
    }
};

/**
 * Calculate the difference in y amounts in the nodeSet
 * @param nodes The nodes
 * @returns {number} The max difference in y
 */
function calcHeight(nodes) {
    if (nodes == null || nodes.length < 1 || Object.keys(nodes).length < 1) {
        return 10;
    }
    var minHeight = nodes[Object.keys(nodes)[0]].y;
    var maxHeight = minHeight;
    $.each(nodes, function (key, node) {
        minHeight = Math.min(minHeight, node.y);
        maxHeight = Math.max(maxHeight, node.y);
    });

    return maxHeight - minHeight + 0.00001;
}

/**
 * Draw the minimap
 * @param nodes
 */
var drawMinimap = function (nodes) {
    if (nodes == null) {
        nodes = minimapNodes;
    } else {
        if (Object.keys(nodes).length == 0) {
            return;
        }
        minimapNodes = {};
        minimapNodes = nodes;
        maxMinimapSize = nodes[Object.keys(nodes)[Object.keys(nodes).length - 1]].x;
        $('#maxCoordinateInput').html(maxMinimapSize);
        minimapHeight = calcHeight(nodes);
    }
    if (nodes == null || Object.keys(nodes).length < 1) {
        return;
    }
    var ratio = $('#minimap').width() / nodes[Object.keys(nodes)[Object.keys(nodes).length - 1]].x;

    var canvas = $('#minimap canvas')[0];
    draw(nodes, canvas, false, canvas.height / minimapHeight, function (x) {
        return x * ratio;
    });
};

/**
 * The global draw method that draws either the minimap or the zoomWindow. Based on a given translate method, the
 * coordinates are translated to the right position.
 * @param points
 * @param c
 * @param translate
 */
function draw(points, c, saveRealCoordinates, yTranslate, xTranslate) {
    if (typeof c == "undefined") {
        return;
    }
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, c.width, c.height);

    var nodeHeight = c.height / 2;
    if (saveRealCoordinates) {
        zoomNodeLocations = [];
    }
    var yTrans = 1;
    var yHeight = Math.max(Math.abs(minY), maxY);
    if (yHeight > $(c).height() / 2) {
        yTrans = $(c).height() / 2.3 / yHeight;
    }

    $.each(points, function (id, point) {

        var xPos = xTranslate(point.x);
        var yPos = nodeHeight + point.y * yTrans;

        drawPoint(ctx, xPos, yPos, 1, point);

        if (saveRealCoordinates) {
            zoomNodeLocations.push({
                x: xPos,
                y: yPos,
                label: point.label,
                id: point.id,
                annotations: point.annotations
            });
        }


        $.each(point.edges, function (key, edge) {
            var target = points[edge.startId];
            if (edge.startId == id) {
                target = points[edge.endId];
            }
            if (target) {
                ctx.beginPath();
                ctx.moveTo(xTranslate(point.x), nodeHeight + point.y * yTrans);
                ctx.lineTo(xTranslate(target.x), nodeHeight + target.y * yTrans);
                var selected = (selectedGenomes.length + selectedMiddleNodes.length);
                if (selected > 8) {
                    ctx.lineWidth = Math.max(1, Math.ceil(edge.weight / ((selectedGenomes.length + selectedMiddleNodes.length) / 3)));
                } else {
                    ctx.lineWidth = edge.weight;
                }
                ctx.strokeStyle = '#' + edge.color;
                ctx.stroke();
                ctx.lineWidth = 1;
                ctx.strokeStyle = '#000000';
            }
        });
    });
}

/**
 * Draw a point in the canvas, this can be a circle, square or triangle in different colors, based on the mutation
 * @param ctx The canvas object
 * @param xPos The x to draw it
 * @param yPos The y to draw it
 * @param multiplier How big it should be drawn
 * @param point The pointData
 */
function drawPoint(ctx, xPos, yPos, multiplier, point) {
    ctx.beginPath();
    ctx.fillStyle = '#FFFFFF';
    ctx.strokeStyle = '#000000';
    var pointMutations = point.mutations;
    if (point.visible && ( !pointMutations || typeof pointMutations == "undefined" || pointMutations.length == 0)) {
        ctx.arc(xPos, yPos, 5 * multiplier, 0, 2 * Math.PI);
    } else if (pointMutations) {
        var mutation = pointMutations[0].replace('"', '');
        var index = mutations.indexOf(mutation);
        var mutSize = mutColors.length;
        var color = mutColors[index % mutSize];
        ctx.fillStyle = '#' + color;
        switch (Math.floor(index / mutSize)) {
            case 0: //Square
                ctx.rect(xPos - 5 * multiplier, yPos - 5 * multiplier, 10 * multiplier, 10 * multiplier);
                break;
            case 1: //Triangle
                ctx.moveTo(xPos, yPos - 6 * multiplier);
                ctx.lineTo(xPos + 6 * multiplier, yPos + 6 * multiplier);
                ctx.lineTo(xPos - 6 * multiplier, yPos + 6 * multiplier);
                break;
            case 2: //Square
                ctx.moveTo(xPos - 6 * multiplier, yPos - 6 * multiplier);
                ctx.lineTo(xPos + 6 * multiplier, yPos + 6 * multiplier);
                ctx.moveTo(xPos - 6 * multiplier, yPos + 6 * multiplier);
                ctx.lineTo(xPos + 6 * multiplier, yPos - 6 * multiplier);
                ctx.strokeStyle = '#' + color;
                break;
        }
        ctx.fill();
    }
    ctx.closePath();
    ctx.stroke();
    ctx.fillStyle = '#FFFFFF';
    ctx.strokeStyle = '#000000';
}

/**
 * The method that does the zooming, based on your mouse position, the zooming is applied.
 * @param direction
 * @param zoomAmount
 * @param xMousePos
 */
function zoom(direction, zoomAmount, xMousePos) {
    var slider = $('.slider', '#minimap');
    var minimap = $('#minimap');
    var zoomWindow = $('#zoomWindow');
    var maxWidth = minimap.width();
    var currentWidth = slider.width();
    if (zoomWidth < 10) {
        zoomAmount /= 11 - zoomWidth;
    }
    zoomWidth += (direction > 0) ? -1 * zoomAmount : zoomAmount;
    zoomWidth = Math.max(0.1, Math.min(zoomWidth, 100));

    var newWidth = Math.max(1, Math.min(maxWidth, zoomWidth / 100 * maxWidth));

    var left = parseFloat(slider.css('left').replace('px', ''));
    var difference = Math.abs(currentWidth - newWidth);

    //If zooming in the zoomField should zoom towards the cursor
    if (direction > 0) {
        var mouseX = xMousePos;
        var leftSide = mouseX - left;
        var rightSide = left + currentWidth - mouseX;
        var dif = Math.min(difference, leftSide - rightSide);
        if (dif > 0) {
            left += dif + (difference - dif) / 2;
        } else {
            left += Math.max(0, difference + dif) / 2;
        }
    } else {
        left -= difference / 2;
    }

    left += Math.min(0, maxWidth - (left + newWidth)); //Fix slider going out of right screen.
    left = Math.max(0, Math.min(maxWidth, left));
    slider.css('left', left + 'px');
    slider.width(newWidth + 'px');
    clearTimeout(zoomTimeout);
    //If not zooming anymore, update the ZoomWindow with new data.
    if (zoomAmount != 0) {
        zoomTimeout = setTimeout(function () {
            updatezoomWindow();
        }, 500);
    }
}

/**
 * Update the window that shows the zoomed genome
 */
function updatezoomWindow() {
    if (minimapNodes) {
        var zoom = updateZoomValues();
        var boundingBox = {xleft: Math.floor(zoomLeft), xright: Math.ceil(zoomRight), zoom: Math.ceil(zoom), isMiniMap: false};
        getNodes(boundingBox, drawZoom);
    }
}

/**
 * When scrolling in the zooming view, the zooming needs to be done on the fly. This is done by updating the zoomValues.
 * @returns {number} The amount of zooming.
 */
function updateZoomValues() {
    if (Object.keys(minimapNodes).length < 1) {
        return;
    }
    var slider = $('#minimap .slider');
    var totalWidth = $('#minimap').width();
    var sliderLeft = pxToInt(slider.css('left'));
    var xWidth = minimapNodes[Object.keys(minimapNodes)[Object.keys(minimapNodes).length - 1]].x;
    zoomLeft = (sliderLeft / totalWidth * xWidth);
    zoomRight = ((sliderLeft + slider.width()) / totalWidth * xWidth);
    return (totalWidth / slider.width());
}

/**
 * This function translates from one representation of a bounding box in gui coordinates to
 * coordinates expected by the REST api.
 * @param left The left side
 * @param width The width of the viewing block
 * @param zoom The level of zooming
 * @param isMiniMap Whether it is being drawn in the minimap or in the zooming map.
 * @returns {{xleft: *, xright: *, zoom: *, isMiniMap: *}}
 */
function computeBoundingBox(left, width, zoom, isMiniMap) {
    return {
        'xleft': left,
        'xright': (left + width),
        zoom: zoom,
        isMiniMap: isMiniMap
    }
}

/**
 * Parse the received node data from the server to something that we can use better.
 * @param nodes
 * @returns {{}}
 */
function parseNodeData(nodes) {
    var result = {};

    if (typeof nodes == 'undefined' || nodes.length == 0) {
        return result;
    }

    $.each(nodes, function (key, value) {
        minY = Math.min(minY, value.y);
        maxY = Math.max(maxY, value.y);
        result[value.id] = value;
    });
    return result;
}

/**
 * Fetch the nodes from the server based on a boundingBox.
 * @param boundingBox
 * @param callback
 */
function getNodes(box, callback) {
    if (isNaN(box.xleft) || isNaN(box.xright) || isNaN(box.zoom)) {
        return;
    }
    $.ajax({
        url: url + 'api/getnodes',
        dataType: 'JSON',
        type: 'GET',
        data: box
    }).done(function (data) {
        callback(parseNodeData(data.cList));
    });
}

/**
 * Initialize both canvasses.
 */
function initialize() {
    initializeMinimap();
    initializeZoomWindow();
    initLegendCanvas();
}

/**
 * Draw the canvas in which the different mutationDrawings are shown
 */
function initLegendCanvas() {
    var height = mutations.length * 26 + 20;
    $('#legendaCanvas').html('<canvas width="280px" height="' + height + 'px"></canvas>')
    var canvas = $('#legendaCanvas').find('canvas');
    var ctx = canvas[0].getContext("2d");
    $.each(mutations, function (key, mutation) {
        drawPoint(ctx, 15, 10 + key * 30, 2, {visible: 1, mutations: [mutation]});
        ctx.font = "15px Georgia";
        ctx.fillText(mutation, 35, 16 + key * 30);
    });
}

/**
 * Initialize the minimap canvas
 */
function initializeMinimap() {
    var minimap = $('#minimap');

    minimap.height($('#minimapContainer').height());

    var slider = minimap.find('.slider');

    minimap.find('.canvasContainer').html(
        $('<canvas/>', {'class': 'genomeCanvas', Width: minimap.width(), Height: minimap.height()})
    );

    var boundingBox = computeBoundingBox(0, 10000000, 1, true);
    getNodes(boundingBox, drawMinimap);
    zoom(-1, 1, 1);
}

/**
 * Initialize the zooming window canvas.
 */
function initializeZoomWindow() {
    var zoomWindow = $('#zoomWindow');
    zoomWindow.prepend(
        $('<canvas/>', {'class': 'zoomedCanvas', Width: zoomWindow.width(), Height: zoomWindow.height()})
    );
}

/**
 * Make the phylogeny Panel small, and the minimap Panel big
 */
function fullSizeMinimap() {
    $("#zoom").animate({
        height: screenHeight - 250 - $('#header').height()
    }, 500);

    $('#phylogenyContainer').animate({
        'width': 30
    }, 1000, function () {
        zoom(1, 0, 1);
        screenResize();
    });
}

function goToX(x, zoom) {
    var left = Math.floor(x / maxMinimapSize * $('#minimap').width());
    var width = $('#minimap').width() / zoom;
    zoomWidth = 1;
    $('#minimap .slider').animate({
        'left': left,
        'width': width
    }, 1000, function() {
        updatezoomWindow();
    });
}

function drawScale(c) {
    var ctx = c.getContext("2d");
    var points = 10;
    var total = zoomRight - zoomLeft;
    ctx.font = "15px Georgia";

    for (var x=1; x < points; x++) {
        ctx.beginPath();
        var xPos = x / points * c.width;
        ctx.moveTo(xPos, c.height);
        ctx.lineTo(xPos, c.height - 20);
        ctx.stroke();

        var text = ""+ Math.round(zoomLeft + x / points * total);
        ctx.fillStyle = 'black';
        ctx.fillText(""+ text, xPos - text.length * 4, c.height - 27);
    }

}