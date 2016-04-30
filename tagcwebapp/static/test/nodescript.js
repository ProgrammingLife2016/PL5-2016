var viewportleft = -150;
var viewportright = 250;
var viewporttop = -50;
var viewportbtm = 150;
var zoomstep = 1.1;

function translateX(coordinate) {
	var vpwidth = viewportright - viewportleft;
	var vpheight = viewportbtm - viewporttop;
	var canvas = document.getElementById("myCanvas");
	var canvasWidth = canvas.width;
	var canvasHeight = canvas.height;
	return ((coordinate - viewportleft) / vpwidth) * canvasWidth
}

function translateY(coordinate) {
	var vpwidth = viewportright - viewportleft;
	var vpheight = viewportbtm - viewporttop;
	var canvas = document.getElementById("myCanvas");
	var canvasWidth = canvas.width;
	var canvasHeight = canvas.height;
	return ((coordinate - viewporttop) / vpheight) * canvasHeight
}

function translatewidth(width) {
	var vpwidth = viewportright - viewportleft;
	var canvas = document.getElementById("myCanvas");
	var canvasWidth = canvas.width;
	return ((width) / vpwidth) * canvasWidth
}

function translateheight(height) {
	var vpheight = viewportbtm - viewporttop;
	var canvas = document.getElementById("myCanvas");
	var canvasHeight = canvas.height;
	return ((height) / vpheight) * canvasHeight
}

function translate(x, y, width, height) {
    var result = {'x': x, 'y': y, 'width': width, 'height': height};

    result.x = translateX(x);

}
