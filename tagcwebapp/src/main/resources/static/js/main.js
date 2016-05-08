
var animationSpeed = 1000;
var currentHover = null;
var zoomTimeout = null;
var url = 'http://localhost:9998/';
var ratio = 0;
var minHeight = 300;
var yZoom = 1;

$('document').ready(function() {



    //Update splashscreen
    splashScreen(function() {
        $('body').bind('mousewheel DOMMouseScroll', function(e) {
            if ($(currentHover).hasClass('minimap')) {
                e.preventDefault();
                zoom(e.originalEvent.wheelDelta);
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

        initialize();
    });
});

function splashScreen(callback)
{
    $('#splashscreen')
        .fadeIn(animationSpeed)
        .animate({'left':'0', 'top':'0', 'width':'300px'}, animationSpeed, function() {
            var width = ($( window ).width() - 2) + 'px';
            $('#header').show().animate({'left':'0', 'top':'0', 'width':width}, animationSpeed, function() {
                $('#container').fadeIn();
                callback();
            })
        });
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

var drawGenome = function(nodes) {
    draw(nodes, $('#minimap canvas')[0], function(x, y) {
        return {
            x: x / ratio,
            y: y / ratio
        }
    });
};

function draw(data, c, translate) {
    var points = data.cList;
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, c.width, c.height);
    $.each(points, function(id, value) {
        ctx.beginPath();
        var coor = translate(value.xCoordinate, value.yCoordinate);

        ctx.arc(coor.x, coor.y, value.weight / 10, 0, 2 * Math.PI);
        ctx.stroke();
        $.each(value.edges, function(key, edge) {
            if (edge.targetX != -1 && edge.targetY != -1) {
                ctx.beginPath();
                ctx.moveTo(coor.x, coor.y);
                var targetCoor = translate(edge.targetX, edge.targetY);
                ctx.lineTo(targetCoor.x, targetCoor.y);
                ctx.lineWidth = edge.weight;
                ctx.stroke();
            }
        });
    });
}

function zoom(direction) {
    var slider = $('.slider', currentHover);
    var minimap = $('#minimap');
    var zoomWindow = $('#zoomWindow');
    var currentWidth = slider.width();
    var currentHeight = slider.height();
    var maxWidth = minimap.width();
    var maxHeight = minimap.height();
    var zoomRatio = zoomWindow.height() / zoomWindow.width();
    var newWidth = 0;
    if (direction > 0) {
        newWidth = Math.max(Math.min(currentWidth * 0.95, currentWidth - 2), 1);
    }
    else {
        newWidth = Math.min(Math.max(currentWidth * 1.05, currentWidth + 2), maxWidth);
    }
    var newHeight = Math.max(currentWidth * zoomRatio, 1);
    if (newHeight > maxHeight) {
        newWidth = maxHeight / zoomRatio;
        newHeight = maxHeight;
    }
    var left = parseInt(slider.css('left').replace('px', ''));
    left = Math.max(0, left + parseInt((currentWidth - newWidth) / 2));
    if ((left + newWidth) > maxWidth) {
        left = maxWidth - newWidth;
    }
    var top = parseInt(slider.css('top').replace('px', ''));
    top = Math.max(0, top + parseInt((currentHeight - newHeight) / 2));
    if ((top + newHeight) > maxHeight) {
        top = maxHeight - newHeight;
    }
    slider.css('left', left + 'px');
    slider.css('top', top + 'px');
    slider.width(parseInt(newWidth) +'px');
    slider.height(parseInt(newHeight) +'px');
    clearTimeout(zoomTimeout);
    zoomTimeout = setTimeout(function() { updatezoomWindow() }, 500);
}

function updatezoomWindow()
{
    var slider = $('#minimap .slider');
    var x = Math.floor(slider.position().left - slider.parent().position().left);
    var y = Math.floor(slider.position().top - slider.parent().position().top);
    var width = slider.width();
    var height = slider.height();
    var boundingBox = computeBoundingBox(x, y, width, height)
    getNodes(boundingBox, drawZoom);
}

// This function translates from one representation of a bounding box in gui coordinates to
// coordinates expected by the REST api. It takes x, y, width and height arguments and returns 
// an object with left right top and bottom properties.  
function computeBoundingBox(x, y, width, height)
{
	return {
            'xleft': x * ratio,
            'xright': (x + width) * ratio,
            'ytop': y / yZoom * ratio,
            'ybtm': (y + height) / yZoom * ratio
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
    $.ajax({
        url: url + 'api/getdimensions',
        dataType: 'JSON',
        type: 'GET'
    }).done(function(data) {
        var minimap = $('#minimap');
        var height = minimap.width() * (data.height / data.width);
        if (height < minHeight) {
            yZoom = Math.floor(minHeight / height);
            height *= yZoom;
        }

        minimap.height(height);

        var slider = minimap.find('.slider');

        ratio = data.width / minimap.width();

        minimap.prepend(
            $('<canvas/>', {'class':'genomeCanvas', Width: minimap.width(), Height: minimap.height() })
        );
        var boundingBox = computeBoundingBox(0, 0, minimap.width(), minimap.height());
        getNodes(boundingBox, drawGenome);
        zoom(-1);
    });
}

function initializeZoomWindow() {
    var zoomWindow = $('#zoomWindow');
    zoomWindow.prepend(
        $('<canvas/>', {'class':'zoomedCanvas', Width: zoomWindow.width(), Height: zoomWindow.height() })
    );
}