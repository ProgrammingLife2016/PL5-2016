var color_scheme = d3.scale.category10();
var cached_json_data;
var tree = d3.layout.phylotree("body").separation(
    function(a, b) {
        return 0;
    });

var container_id = '#tree_container';
var svgPanZoomObject;

$(document).ready(function() {
    addCompareGenomeButtonBindings();
    set_default_tree_settings();
    //makeRestAPIcall('getnewickstring','JSON', 'GET', '', drawTree);
    makeRestAPIcall('getphylogenetictree','JSON', 'GET', {'treeId' : 1}, drawTree);
});

function drawTree(json) {
	var json = refreshCache(json);	 
	var isJSONtree = containsJSONtree(json);
	var tree_data = getTreeData(json, isJSONtree);
	
    var width = $("#treeViewPort").width();
    var height = $("#treeViewPort").height();
    var svg = d3.select(container_id).append("svg").attr("width", width).attr(
        "height", height);
    tree.size([height, width])(tree_data, isJSONtree).svg(svg).layout();
    enablePanZoom();
}
function refreshCache(json)
{
	if(json == null)
		{
		return cached_json_data;
		}
	else {
		cached_json_data = json;
		return json;
	}
}

function containsJSONtree(obj)
{
	if(obj == null)
	{
		return false;	
	}
	else
	{	
		return obj.name != null && obj.name == "root";
	}
}

function getTreeData(json, isJSONtree)
{
	if(isJSONtree)
	{		
		return json;	
	}
	else if(json != null )
	{
		return json.newickString;
	}
}

function enablePanZoom(){
	var width = $("#treeViewPort").width();
	var height = $("#treeViewPort").height();
	var philoSVG = $("#tree_container svg");
	philoSVG.attr("width", width);
	philoSVG.attr("height", height);
	philoSVG.attr("viewBox", "0 0 " + width + " " + height);
	svgPanZoomObject = svgPanZoom(philoSVG.get(0),{
		controlIconsEnabled: true, 
		minZoom:0,
		maxZoom:100,
		zoomScaleSensitivity:0.8
			});
	}

function addCompareGenomeButtonBindings() {

    $('#compGenomesButton').on('click', function(e) {
        var selectedNodeObjects = tree.get_selection();
        var names = [];
        selectedNodeObjects.forEach(function(d) {
            console.log('test');
            console.log(d);
            if (d.name) {
            	names.push(d.name);
            }
        });
        console.log(names);

        var data = {
            'names' : names
        };

        fullSizeMinimap();

        makeRestAPIcall('setactivegenomes', 'JSON', 'POST', data, handleActivationResponse);
        initializeMinimap();
    })
}

function makeRestAPIcall(apiCall, dataType, requestType, reqData, callback) {
    var url = 'http://localhost:9998/api/';
    //Temp:
    // url = 'http://localhost:9998/app/';
    $.ajax({
        url : url + apiCall,
        dataType : dataType,
        type : requestType,
        data : reqData
    }).done(function(respData) {
        callback(respData);
    });
}

//Handles the genome activation response. The response contains a list 
//of unrecognized genomes. If this list is not empty it will activate
//a dialog in the GUI with the unrecognized genomes and a message.
function handleActivationResponse(response) {
    console.log("handleActivationResponse function called with data:");
    console.log(response);
    if(response.list.length != 0)
    	{    	
    	showDialog("Warning: Unrecognized genomes",createUnrecognizedGenomesMessage(response.list));
    	}
    initializeMinimap();
}

//creates an unrecognized genomes message from a list of genome id's
function createUnrecognizedGenomesMessage(list)
{
	var message = "The following genomes were not found in the genome graph:<br>"
		list.forEach(function(genome){
			message = message + genome + "<br>";
		})
	return message;
}

//Shows a dialog with the specified header and body
function showDialog(header, body)
{
	$("#myModal .modal-title").html(header);
	$("#myModal .modal-body>p").html(body);
	$('#myModal').modal('show');
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
