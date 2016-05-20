
var animationSpeed = 1000;
var currentHover = null;
var zoomTimeout = null;
var url = 'http://localhost:9998/';
var ratio = 0;
var minHeight = 300;
var yZoom = 1;
var screenWidth = $(window).width();
var screenHeight = $(window).height();
var screenResizeTimeout, treeRedrawTimeout;
var zoomWidth = 100;
var cachedNodes;
var currentMousePos = { x: -1, y: -1 };
var counter = 1;

function screenResize() {
    if ($(window).width() != screenWidth) {
        $("#phylogenyContainer").width(Math.ceil($("#phylogenyContainer").width() * $(window).width() / screenWidth));
        screenWidth = $(window).width();
    }

    if ($(window).height() != screenHeight) {
        $("#zoom").height(Math.ceil($("#zoom").height() * $(window).height() / screenHeight));
        screenHeight = $(window).height();
    }

    if ($("#zoom").height() + $("#header").height() > (screenHeight - 25)) {
        $("#zoom").height(screenHeight - 25 - $("#header").height());
    }

    //Update height
    var borderHeight = parseInt($("#zoom").css("border-bottom-width").replace('px', ''));
    $('#sub').height($(window).height() - $("#zoom").height() - $("#header").height() - borderHeight);
    $('#sub').height($(window).height() - $("#zoom").height() - $("#header").height() - borderHeight);
    $('#minimap').height($('#sub').height());
    if ($('#zoom').find('canvas').length) {
        $('#zoom').find('canvas')[0].height = $('#zoom').height();
        $('#zoom').find('canvas')[0].width = $('#zoom').width();
        updatezoomWindow();
    }

    //Update sub width
    var borderWidth = parseInt($("#phylogenyContainer").css("border-right-width").replace('px', ''));
    $('#minimapContainer').width($("#sub").width() - $("#phylogenyContainer").width() - borderWidth);
    if ($('#minimapContainer').find('canvas').length) {
        $('#minimap').find('canvas')[0].width = $('#minimap').width();
        $('#minimap').find('canvas')[0].height = $('#minimap').height();
        drawMinimap(null); //Update the canvas
        zoom(1, 0);
        if (treeRedrawTimeout) {
            clearTimeout(treeRedrawTimeout);
        }
        treeRedrawTimeout = setTimeout(function() {
            //drawTree(null); //Update the phylogenyTree
        }, 1500);
    } 
}

$('document').ready(function() {

    screenResize();

    $("#zoom").resizable({
        handles: 's'
    });
    $("#phylogenyContainer").resizable({
        handles: 'e'
    });
    $('#zoom').resize(function() {
        if (screenResizeTimeout) {
            clearTimeout(screenResizeTimeout);
        }
        screenResizeTimeout = setTimeout(function() {
            screenResize();
        }, 500);
    });

    $('#phylogenyContainer').resize(function() {
        if (screenResizeTimeout) {
            clearTimeout(screenResizeTimeout);
            console.log("timeOut");
        }
        screenResizeTimeout = setTimeout(function() {
            screenResize();
        }, 500);
    });

    $(window).resize(function() {
        if (screenResizeTimeout) {
            clearTimeout(screenResizeTimeout);
        }
        screenResizeTimeout = setTimeout(function() {
            screenResize();
        }, 500);
    });


    $('#zoomoutbtn').on('click', function() {
        zoom(-1, 10);
    });
    $('#zoominbtn').on('click', function() {
        zoom(1, 10);
    });


    $('body').bind('mousewheel DOMMouseScroll', function(e) {
        if ($(currentHover).is('#minimap')) {
            e.preventDefault();
            zoom(e.originalEvent.wheelDelta, 1);
        }
    });

    //Add the slider
    var slider = $('#minimap').find('.slider');
    $(slider)
        .draggable({
            containment: "parent",
            drag: function() {
                //Temp, to make it look smooth, can be done if lazy loading is implemented
                updatezoomWindow();

                //Should be this when we have specific zoomData
                //clearTimeout(zoomTimeout);
                //zoomTimeout = setTimeout(function() { updatezoomWindow() }, 50);
            }
        });

    $('.genome').hover(function() {
        currentHover = this;
    }, function() {
        currentHover = null;
    });

    $('#minimapContainer').mousemove(function(event) {
        currentMousePos.x = event.pageX;
        currentMousePos.y = event.pageY;
    });

    initialize();
});

function resizeBlocks() {

}

function pxToInt(css) {
    return parseFloat(css.replace('px', ''));
}

var drawZoom = function(nodes) {
    var ratio = $('#zoomWindow').width() / Object.keys(nodes).length;
    var left = nodes[Object.keys(nodes)[0]].x - 1;

    draw(nodes, $('#zoomWindow canvas')[0], function(x) {
        return (x - left) * ratio;
    });
};

var drawMinimap = function(nodes) {
    if (nodes == null) {
        nodes = cachedNodes;
    } else {
        cachedNodes = nodes;
    }
    var ratio = $('#minimap').width() / Object.keys(nodes).length;

    draw(nodes, $('#minimap canvas')[0], function(x) {
        return x * ratio;
    });
};

function draw(points, c, translate) {
    if (typeof c == "undefined") {
        return;
    }
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, c.width, c.height);

    var count = 20;
    var counter = 0;
    var nodeHeight = c.height / 2;

    var yTranslate = (c.height < 200)?c.height / 2 / 110 : 1;

    $.each(points, function(id, point) {
        ctx.beginPath();
        ctx.arc(translate(point.x), nodeHeight + point.y * yTranslate, 5, 0, 2 * Math.PI);
        ctx.stroke();

        $.each(point.edges, function(key, edge) {
            var target = points[edge.start];
            if (edge.start == id) {
                target = cachedNodes[edge.end];
            }
            if (target) {
                ctx.beginPath();
                ctx.moveTo(translate(point.x), nodeHeight + point.y * yTranslate);
                ctx.lineTo(translate(target.x), nodeHeight + target.y * yTranslate);
                ctx.lineWidth = edge.weight;
                ctx.stroke();
                ctx.lineWidth = 1;
            }
        });
    });
}

function zoom(direction, zoomAmount) {
    var slider = $('.slider', currentHover);
    var minimap = $('#minimap');
    var zoomWindow = $('#zoomWindow');
    var maxWidth = minimap.width();
    var currentWidth = slider.width();
    zoomWidth += (direction > 0)?-1*zoomAmount:zoomAmount;
    zoomWidth = Math.max(1, Math.min(zoomWidth, 100));

    var newWidth = Math.max(1, Math.min(maxWidth, zoomWidth / 100 * maxWidth));

    var left = parseInt(slider.css('left').replace('px', ''));
    var difference = Math.abs(currentWidth - newWidth);

    if (direction > 0) {
        var mouseX = currentMousePos.x - minimap.position().left;
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
    slider.width(parseInt(newWidth) +'px');
    clearTimeout(zoomTimeout);
    if (zoomAmount != 0) {
        zoomTimeout = setTimeout(function () {
            updatezoomWindow();
        }, 500);
    }
}

function updatezoomWindow()
{
    var slider = $('#minimap .slider');
    var totalWidth = $('#minimap').width();
    var left = pxToInt(slider.css('left'));
    var width = slider.width();
    //var boundingBox = computeBoundingBox(x, width);
    //getNodes(boundingBox, drawZoom);
    //Temp:
    if (cachedNodes) {
        var nodeSize = Object.keys(cachedNodes).length;

        var start = Math.ceil(left / totalWidth * nodeSize);
        var end = start + Math.ceil(width / totalWidth * nodeSize);

        var nodeList = {};
        var count = 0;
        $.each(cachedNodes, function(key, value) {
            if (count++ >= start) {
                nodeList[key] = value;
            }
            if (count > end) {
                return false;
            }
        });

        drawZoom(nodeList);
    }
}

// This function translates from one representation of a bounding box in gui coordinates to
// coordinates expected by the REST api. It takes x, y, width and height arguments and returns 
// an object with left right top and bottom properties.  
function computeBoundingBox(x, width)
{
    return {
        'xleft': x,
        'xright': (x + width),
        zoom: 3
    }
}

function parseNodeData(nodes) {
    var result = {};

    var left = nodes[0].id;
    var ratio = $('#minimap').width() / (nodes[nodes.length-1].x - left);

    $.each(nodes, function(key, value) {
        result[value.id] = {
            x: value.x - left,
            y: value.y,
            strands: value.strands,
            edges: value.edges,
            genomes: value.genomes
        }
    });
    return result;
}

function getNodes(boundingBox, callback) {
    $.ajax({
        url: url + 'api/getnodes',
        dataType: 'JSON',
        type: 'GET',
        data: boundingBox
    }).done(function(data) {
        callback(parseNodeData(data.cList));
    });
}

function initialize() {
    initializeMinimap();
    initializeZoomWindow();
}

function initializeMinimap() {
    var minimap = $('#minimap');
    minimap.height($('#minimapContainer').height());

    var slider = minimap.find('.slider');

    minimap.find('.canvasContainer').html(
        $('<canvas/>', {'class':'genomeCanvas', Width: minimap.width(), Height: minimap.height() })
    );
    var boundingBox = computeBoundingBox(0, 10000000);
    getNodes(boundingBox, drawMinimap);
    zoom(-1, 1);
}

function initializeZoomWindow() {
    var zoomWindow = $('#zoomWindow');
    zoomWindow.prepend(
        $('<canvas/>', {'class':'zoomedCanvas', Width: zoomWindow.width(), Height: zoomWindow.height() })
    );
}

function fullSizeMinimap() {
    $("#zoom").animate({
        height: screenHeight - 250 - $('#header').height()
    }, 500);

    $('#phylogenyContainer').animate({
        'width': 30
    }, 1000, function () {
        zoom(1, 0);
        screenResize();
    });
}