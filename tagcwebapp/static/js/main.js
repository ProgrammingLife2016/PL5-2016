
var animationSpeed = 0;
var currentHover = null;
var zoomTimeout = null;

$('document').ready(function() {



    //Update splashscreen
    splashScreen(function() {
        $('body').bind('mousewheel DOMMouseScroll', function(e) {
            if ($(currentHover).hasClass('genome')) {
                e.preventDefault();
                var slider = $('.slider', currentHover);
                var currentWidth = slider.width();
                var maxWidth = slider.data('maxwidth');
                var newWidth = 0;
                if (e.originalEvent.wheelDelta > 0) {
                    newWidth = Math.max(Math.min(currentWidth * 0.95, currentWidth - 2), 1);
                }
                else {
                    newWidth = Math.min(Math.max(currentWidth * 1.05, currentWidth + 2), maxWidth);
                }
                var left = parseInt(slider.css('left').replace('px', ''));
                left = Math.max(0, left + parseInt((currentWidth - newWidth) / 2));
                if ((left + newWidth) > maxWidth) {
                    left = maxWidth - newWidth;
                }
                slider.css('left', left + 'px');
                slider.width(parseInt(newWidth) +'px');
                clearTimeout(zoomTimeout);
                zoomTimeout = setTimeout(function() { updateZoom() }, 500);
            }
        });

        //Add the slider
        var genomeSlider = $('#genome').find('.slider');
        $(genomeSlider)
            .data('maxwidth', $(genomeSlider).width())
            .draggable({
                containment: "parent",
                stop: function() {
                    clearTimeout(zoomTimeout);
                    zoomTimeout = setTimeout(function() { updateZoom() }, 500);
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

function updateZoom()
{
    var slider = $('#genome .slider');
    var x = Math.floor(slider.position().left - slider.parent().position().left);
    var y = Math.floor(slider.position().top - slider.parent().position().top);
    var width = slider.width();
    var height = slider.height();
    drawNodes(x, y, width, height, drawZoom)
    console.log(x);
    console.log(y);
}

function getNodes(x, y, width, height, callback) {

    $.getJSON('../getnodes', {
        'xleft' : x,
        'xright' : x + width,
        'ytop' : y,
        'ybtm' : y + height
    }, function(data) {
        callback(data);
    });
}

function initialize() {
    initializeBasicGenome();
}

function initializeBasicGenome() {
    $.ajax({
        url: url + 'getDimensions',
        dataType: 'Json',
        type: 'POST'
    }).done(function(data) {
        var slider = $('#genome .slider');
        getNodes(0, 0, slider.width(), slider.height(), function(nodes) {
            drawGenome(nodes);
            drawZoom(nodes);
        });
    });
}