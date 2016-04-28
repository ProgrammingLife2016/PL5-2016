
var animationSpeed = 1000;
var currentHover = null;

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
                    console.log('up');
                }
                else {
                    newWidth = Math.min(Math.max(currentWidth * 1.05, currentWidth + 2), maxWidth);
                    console.log('down');
                }
                var left = parseInt(slider.css('left').replace('px', ''));
                left = Math.max(0, left + parseInt((currentWidth - newWidth) / 2));
                if ((left + newWidth) > maxWidth) {
                    left = maxWidth - newWidth;
                }
                slider.css('left', left + 'px');
                slider.width(parseInt(newWidth) +'px');
            }
        });

        //Add the slider
        var genomeSlider = $('#genome').find('.slider');
        $(genomeSlider)
            .data('maxwidth', $(genomeSlider).width())
            .draggable({ axis: "x", containment: "parent" });

        $('.genome').hover(function() {
            currentHover = this;
        }, function() {
            currentHover = null;
        })
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