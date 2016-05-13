var width = 800, height = 600, color_scheme = d3.scale.category10();
var tree = d3.layout.phylotree("body").size([ height, width ]).separation(
		function(a, b) {
			return 0;
		})
var container_id = '#tree_container';

$(document).ready(function() {
	addCompareGenomeButtonBindings();
	set_default_tree_settings()
	makeRestAPIcall('getnewickstring','JSON', 'GET', '', drawTree);
});

function drawTree(newickStringJSONObject) {
	var svg = d3.select(container_id).append("svg").attr("width", width).attr(
			"height", height);
	tree(newickStringJSONObject.newickString).svg(svg).layout(); 
}


function addCompareGenomeButtonBindings() {

	$('#compGenomesButton').on('click', function(e) {
		var selectedNodeObjects = tree.get_selection();
		var names = [];
		selectedNodeObjects.forEach(function(d) {
			if (d.name) {
				names.push(d.name);
			}
		});
		console.log(names);
		
		var data = {
			'names' : JSON.stringify(names) 
		}
		
		makeRestAPIcall('getribbongraph','JSON', 'GET', data, drawRibbonGraph);
	})
}

function makeRestAPIcall(apiCall, dataType, requestType, reqData, callback) {
	var url = 'http://localhost:9998/api/';
	$.ajax({
		url : url + apiCall,
		dataType : dataType,
		type : requestType,
		data : reqData
	}).done(function(respData) {
		callback(respData);
	});
}

function drawRibbonGraph(graph) {
	console.log("drawRibbonGraph function called with data:");
	console.log(graph);
}

function set_default_tree_settings() {
	tree.branch_length(null);
	tree.branch_name(null);
	tree.node_span('equal');
	tree.options({
		'draw-size-bubbles' : false
	}, false);
	// tree.radial (true);
	tree.style_nodes(node_colorizer);
	tree.style_edges(edge_colorizer);
}

function node_colorizer(element, data) {
	try {
		var count_class = 0;

		selection_set.forEach(function(d, i) {
			if (data[d]) {
				count_class++;
				element.style("fill", color_scheme(i),
						i == current_selection_id ? "important" : null);
			}
		});

		if (count_class > 1) {

		} else {
			if (count_class == 0) {
				element.style("fill", null);
			}
		}
	} catch (e) {

	}

}

function edge_colorizer(element, data) {
	try {
		var count_class = 0;

		selection_set.forEach(function(d, i) {
			if (data[d]) {
				count_class++;
				element.style("stroke", color_scheme(i),
						i == current_selection_id ? "important" : null);
			}
		});

		if (count_class > 1) {
			element.classed("branch-multiple", true);
		} else if (count_class == 0) {
			element.style("stroke", null).classed("branch-multiple", false);
		}
	} catch (e) {
	}

}
