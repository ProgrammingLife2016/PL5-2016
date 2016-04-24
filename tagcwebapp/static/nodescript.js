var viewportleft = -150;
var viewportright = 250;
var viewporttop = -50;
var viewportbtm = 150;
var zoomstep = 1.1

function translateX(coordinate) {
	var vpwidth = viewportright - viewportleft;
	var vpheight = viewportbtm - viewporttop;
	var canvas = document.getElementById("myCanvas");
	var canvaswidth = canvas.width;
	var canvasheight = canvas.height;
	console.log(((coordinate - viewportleft) / vpwidth) * canvaswidth)
	return ((coordinate - viewportleft) / vpwidth) * canvaswidth
}

function translateY(coordinate) {
	var vpwidth = viewportright - viewportleft;
	var vpheight = viewportbtm - viewporttop;
	var canvas = document.getElementById("myCanvas");
	var canvaswidth = canvas.width;
	var canvasheight = canvas.height;
	console.log(((coordinate - viewporttop) / vpheight) * canvasheight)
	return ((coordinate - viewporttop) / vpheight) * canvasheight
}
function translatewidth(width) {
	var vpwidth = viewportright - viewportleft;
	var canvas = document.getElementById("myCanvas");
	var canvaswidth = canvas.width;
	console.log(((width) / vpwidth) * canvaswidth)
	return ((width) / vpwidth) * canvaswidth
}
function translateheight(height) {
	var vpheight = viewportbtm - viewporttop;
	var canvas = document.getElementById("myCanvas");
	var canvasheight = canvas.height;
	console.log(((height) / vpheight) * canvasheight)
	return ((height) / vpheight) * canvasheight
}
function draw(data) {
	var points = data.cList;
	var c = document.getElementById("myCanvas");
	var ctx = c.getContext("2d");
	ctx.clearRect(0, 0, c.width, c.height);
	$.each(points, function(id, value) {
		console.log(id)
		console.log(value)

		ctx.beginPath();
		ctx.arc(translateX(value.x), translateY(value.y), 5, 0, 2 * Math.PI);
		ctx.stroke();
	});
	ctx.rect(translateX(0), translateY(0), translatewidth(100),
			translateheight(100));
	ctx.stroke();
}
var drawnodes = function() {

	$.getJSON('../getnodes', {
		'xleft' : viewportleft,
		'xright' : viewportright,
		'ytop' : viewporttop,
		'ybtm' : viewportbtm,
	}, function(data) {
		draw(data)
	});
}

var main = function() {
	drawnodes();

	$('#zoomoutbtn').on('click', function() {
		console.log('hh')
		viewportleft = viewportleft * zoomstep;
		viewportright = viewportright * zoomstep;
		viewporttop = viewporttop * zoomstep;
		viewportbtm = viewportbtm * zoomstep;
		drawnodes();
	});
	$('#zoominbtn').on('click', function() {
		console.log('hh')
		viewportleft = viewportleft / zoomstep;
		viewportright = viewportright / zoomstep;
		viewporttop = viewporttop / zoomstep;
		viewportbtm = viewportbtm / zoomstep;
		drawnodes();
	});
};

$(document).ready(main());
