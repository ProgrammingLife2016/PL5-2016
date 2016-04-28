
var animationSpeed = 0;
var currentHover = null;
var zoomTimeout = null;
var url = 'http://localhost:9998/app/';
var ratio = 0;
var minHeight = 300;
var yZoom = 1;

$('document').ready(function() {



    //Update splashscreen
    splashScreen(function() {
        $('body').bind('mousewheel DOMMouseScroll', function(e) {
            if ($(currentHover).hasClass('mainGenome')) {
                e.preventDefault();
                zoom(e.originalEvent.wheelDelta);
            }
        });

        //Add the slider
        var genomeSlider = $('#genome').find('.slider');
        $(genomeSlider)
            .draggable({
                containment: "parent",
                stop: function() {
                    clearTimeout(zoomTimeout);
                    zoomTimeout = setTimeout(function() { updateZoomedGenome() }, 500);
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

var drawZoom = function(nodes) {
    draw(nodes, $('#zoomedGenome canvas')[0]);
};

var drawGenome = function(nodes) {
    draw(nodes, $('#genome canvas')[0]);
};

function draw(data, c) {
    var points = data.cList;
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, c.width, c.height);
    $.each(points, function(id, value) {
        console.log(id);
        console.log(value);

        ctx.beginPath();
        var coor = translate(value.x, value.y);
        ctx.arc(coor.x, coor.y, 5, 0, 2 * Math.PI);
        ctx.stroke();
    });
    ctx.rect(translateX(0), translateY(0), translatewidth(100),
        translateheight(100));
    ctx.stroke();
}

function translate(x, y) {
    var result = {'x': x, 'y': y};
    

}

function zoom(direction) {
    var slider = $('.slider', currentHover);
    var genome = $('#genome');
    var zoomedGenome = $('#zoomedGenome');
    var currentWidth = slider.width();
    var currentHeight = slider.height();
    var maxWidth = genome.width();
    var maxHeight = genome.height();
    var zoomRatio = zoomedGenome.height() / zoomedGenome.width();
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
    //zoomTimeout = setTimeout(function() { updateZoomedGenome() }, 500);
}

function updateZoomedGenome()
{
    var slider = $('#genome .slider');
    var x = Math.floor(slider.position().left - slider.parent().position().left);
    var y = Math.floor(slider.position().top - slider.parent().position().top);
    var width = slider.width();
    var height = slider.height();
    getNodes(x, y, width, height, drawZoom)
    console.log(x);
    console.log(y);
}

function getNodes(x, y, width, height, callback) {

    $.ajax({
        url: url + '../getnodes',
        dataType: 'JSON',
        type: 'GET',
        data: {
            'xleft': x,
            'xright': x + width,
            'ytop': y,
            'ybtm': y + height
        }
    }, function(data) {
        callback(data);
    });
}

function initialize() {
    initializeBasicGenome();
}

function initializeBasicGenome() {
    $.ajax({
        url: url + 'getdimensions',
        dataType: 'JSON',
        type: 'GET'
    }).done(function(data) {
        ratio = data.height / data.width;
        var genome = $('#genome');
        var height = genome.width() * ratio;
        if (height < minHeight) {
            yZoom = Math.floor(minHeight / height);
            height *= yZoom;
        }

        genome.height(height);

        var slider = genome.find('.slider');

        getNodes(0, 0, slider.width(), slider.height(), function(nodes) {
            drawGenome(nodes);
            drawZoom(nodes);
        });
    });
}