
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

function screenResize() {
    if ($(window).width() != screenWidth) {
        $("#phylogenyContainer").width(Math.ceil($("#phylogenyContainer").width() * $(window).width() / screenWidth));
        screenWidth = $(window).width();
    }

    if ($(window).height() != screenHeight) {
        console.log(screenHeight);
        console.log($(window).height());
        $("#zoom").height(Math.ceil($("#zoom").height() * $(window).height() / screenHeight));
        screenHeight = $(window).height();
    }

    //Update height
    var borderHeight = parseInt($("#zoom").css("border-bottom-width").replace('px', ''));
    $('#sub').height($(window).height() - $("#zoom").height() - $("#header").height() - borderHeight);
    $('#sub').height($(window).height() - $("#zoom").height() - $("#header").height() - borderHeight);
    if ($('#zoom').find('canvas').length) {
        $('#zoom').find('canvas').height = $('#zoom').height();
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
            drawTree(null); //Update the phylogenyTree
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
        screenResize();
    });
    $('#phylogenyContainer').resize(function() {
        screenResize();
    });

    $(window).resize(function() {
        if (screenResizeTimeout) {
            clearTimeout(screenResizeTimeout);
        }
        screenResizeTimeout = setTimeout(function() {
            screenResize();
        }, 100);
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
            stop: function() {
                clearTimeout(zoomTimeout);
                zoomTimeout = setTimeout(function() { updatezoomWindow() }, 500);
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
    draw(nodes, $('#zoomWindow canvas')[0], function(x, y) {
        var slider = $('#minimap .slider');
        var sliderRatio = $('#zoomWindow').width() / slider.width();
        return {
            x: (x / ratio - pxToInt(slider.css('left'))) * sliderRatio,
            y: (y / ratio - pxToInt(slider.css('top'))) * sliderRatio
        }
    });
};

var drawMinimap = function(nodes) {
    if (nodes == null) {
        nodes = cachedNodes;
    } else {
        cachedNodes = nodes;
    }
    draw(nodes, $('#minimap canvas')[0], function(x, y) {
        return {
            x: x / ratio * 50,
            y: y / ratio * 50
        }
    });
};

function draw(data, c, translate) {
    if (typeof c == "undefined") {
        return;
    }
    var points = data.cList;
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, c.width, c.height);

    var count = 0;

    $.each(points, function(id, value) {
        ctx.beginPath();
        var coor = translate(value.xCoordinate, value.yCoordinate);

        ctx.arc(count++, c.height / 2, 5, 0, 2 * Math.PI);
        ctx.stroke();
        //$.each(value.edges, function(key, edge) {
        //    if (edge.targetX != -1 && edge.targetY != -1) {
        //        ctx.beginPath();
        //        ctx.moveTo(coor.x, coor.y);
        //        var targetCoor = translate(edge.targetX, edge.targetY);
        //        ctx.lineTo(targetCoor.x, targetCoor.y);
        //        ctx.lineWidth = edge.weight;
        //        ctx.stroke();
        //    }
        //});
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
    var x = Math.floor(slider.position().left - slider.parent().position().left);
    var width = slider.width();
    var boundingBox = computeBoundingBox(x, width);
    getNodes(boundingBox, drawZoom);
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

function getNodes(boundingBox, callback) {
    $.ajax({
        url: url + 'api/getnodes',
        dataType: 'JSON',
        type: 'GET',
        data: boundingBox
    }).done(function(data) {
        callback(data);
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
    $('#phylogenyContainer').animate({
        'width': 30
    }, 1000, function() {
        zoom(1, 0);
        screenResize();
    });
}