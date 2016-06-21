/**
 * Created by Thomas on 09-06-16.
 */
var phyloTree = {};
var phyloRoot;
var currentNode = -1;
var back = [];
var selectedGenomes = [];
var selectedMiddleNodes = [];
var phyloColors = [];
var genomes = [];
var nodeClickTimer;

$("document").ready(function() {

    //Load the phylogenic Tree
    $.ajax({
        url: url + "api/getphylogenetictree?treeId=1",
        dataType: "json",
        type: "get"
    }).done(function (data) {
        data.id = -1;
        parsePhyloTree(data);
        searchGenome();
    });

    //Add an onclick on the links in the phyloTree, zooming in or selecting the genomes
    $("#svgCanvas").on("click", "a", function (e) {
        e.preventDefault();
        var id = $(this).attr("href").replace("#", "");
        var node = phyloTree[id];
        if (node.children.length > 0) {
            back.push(currentNode);
            currentNode = id;
            $('#phyloBackButton').show();
            drawPhyloTree(id);
        } else {
            selectGenome(phyloTree[id].name);
        }
    });

    //Send the selected genomes to the server and update the minimap
    $('#compGenomesButton').on("click", function (e) {
        e.preventDefault();

        //fullSizeMinimap();

        var names = selectedGenomes.concat(selectedMiddleNodes);

        if (names.length > 8) {
            if (confirm("You selected more than 8 genomes, we highly recommend to select families instead of single genomes.")) {
                $.ajax({

                    url: url + 'api/setactivegenomes',
                    dataType: 'JSON',
                    type: 'POST',
                    data: {'names': names}
                }).done(function (respData) {
                    initializeMinimap();
                });
            }
        }
    });

    //Move back up in the phyloTree
    $('#phyloBackButton').click(function (e) {
        e.preventDefault();
        var id = back.pop();
        currentNode = id;
        if (back.length == 0) {
            $(this).hide();
        }
        drawPhyloTree(id);
    });

    //Make the phyloTreeView big in an animated way.
    $('#expandPhyloView').click(function (e) {
        e.preventDefault();
        $("#zoom").animate({
            height: screenHeight - 800 - $('#header').height()
        }, 500);

        $('#phylogenyContainer').animate({
            'width': 800
        }, 1000, function () {
            zoom(1, 0, 1);
            screenResize();
        });
    });

    $('body').on('mousedown', 'svg circle', function (e) {
        e.preventDefault();
        var nodeId = $(this).attr('nodeid');
        nodeClickTimer = setTimeout(function() {
            selectMultipleGenomes(nodeId);
        }, 500);
        selectMiddleNode(nodeId);
    });

    $('body').mouseup(function() {
        clearTimeout(nodeClickTimer);
    });

    $('body').on('click', '#selectedGenomeList li:not(".firstLi")', function() {
        if ($(this).hasClass('genome')) {
            selectGenome($(this).data('name'));
        } else {
            selectMiddleNode($(this).data('id'));
        }
    });

    $('#phyloZoomLevel').change(function() {
        resizePhyloTree();
    });
});

function selectGenome(genome) {
    $('svg tspan:contains("'+ genome +'")').parents('a').toggleClass('selected');
    var index = $.inArray(genome, selectedGenomes);
    if (index > -1) {
        selectedGenomes.splice(index, 1);
        $('#selectedGenomeList').find('.genome.'+ genome).remove();
    } else {
        selectedGenomes.push(genome);
        $('#selectedGenomeList ul').append('<li class="genome '+ genome +'" data-name="'+ genome +'">'+ genome +'</li>');
    }
}

function selectMiddleNode(nodeId) {
    nodeId = parseInt(nodeId);
    $('svg circle[nodeid="'+ nodeId +'"]').toggleClass('selected');
    var index = $.inArray(nodeId, selectedMiddleNodes);
    if (index > -1) {
        selectedMiddleNodes.splice(index, 1);
        $('#selectedGenomeList').find('.middleNode.'+ nodeId).remove();
    } else {
        selectedMiddleNodes.push(nodeId);
        $('#selectedGenomeList ul').append('<li class="middleNode '+ nodeId +'" data-id="'+ nodeId +'">GenomeFamily '+ nodeId +'</li>');
    }
}

/**
 * Resize the phyloTree based on it's container
 */
function resizePhyloTree() {
    drawPhyloTree(currentNode);
}

/**
 * Parse the data so the system can work with it
 * @param data The data to be parsed
 * @returns {{id: (number|*), count: number}} An object containing nodes, with reference to their children and their size.
 */
function parsePhyloTree(data) {
    var count = 0;
    var children = [];
    var result = {};
    $.each(data.children, function (key, child) {
        result = parsePhyloTree(child);
        count += result.count;
        children.push(result.id);
    });

    phyloTree[data.id] = {
        children: children,
        count: count,
        name: (count > 0) ? count + " Genomes" : data.name
    };

    if (count == 0) {
        genomes.push(data.name);
    }

    return {id: data.id, count: Math.max(1, count)};
}

/**
 * Draw the tree
 * @param root The root node from which it is drawn
 */
function drawPhyloTree(root) {
    var svgCanvas = $('#svgCanvas');
    svgCanvas.find('svg').remove();
    phyloRoot = root;
    initSmits();
    var depth = $('#phyloZoomLevel').val();
    var data = '<phyloxml xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.phyloxml.org http://www.phyloxml.org/1.10/phyloxml.xsd" xmlns="http://www.phyloxml.org"><phylogeny rooted="false">';
    data += '<render><parameters><circular><bufferRadius>0.5</bufferRadius></circular><rectangular><alignRight>1</alignRight></rectangular></parameters>';
    data += '<charts><genome0 type="binary" thickness="10"/><genome1 type="binary" thickness="10"/><genome2 type="binary" thickness="10"/><genome3 type="binary" thickness="10"/></charts>';
    data += '<styles>'+ getPhyloColorStyles() +'</styles></render>';
    data += phyloToXml(root, depth, 0);
    data += '</phylogeny></phyloxml>';

    var dataObject = {
        phyloxml: data,
        fileSource: false
    };

    new Smits.PhyloCanvas(
        dataObject,
        "svgCanvas",
        svgCanvas.width() - 60, svgCanvas.height() - 60,
        "circular"
    );

    $('svg a').each(function (key, value) {
        if ($.inArray(value.textContent, selectedGenomes) > -1) {
            $(value).addClass('selected');
        }
    });

    $('svg circle').each(function(key, value) {
        var nodeId = parseInt($(value).attr('nodeid'));
        if ($.inArray(nodeId, selectedMiddleNodes) > -1) {
            $(value).addClass('selected');
        }
    });
}

/**
 * Parse the phyloData from the server to xml that the jsphylosvg library can read
 * @param nodeId The nodeId to parse
 * @param maxDepth The maxDepth that the tree can go
 * @param depth The current depth
 * @returns {*}
 */
function phyloToXml(nodeId, maxDepth, depth) {
    if (depth > maxDepth) {
        return "";
    }
    var node = phyloTree[nodeId];
    var childrenXml = "";
    if (depth > 0) {
        childrenXml += "<branch_length>1.0</branch_length>";
    }
    if (node.children.length == 0 || depth == maxDepth) {
        heat = Math.min(16, node.count / 2);
        color = 'none';
        $.each(phyloColors, function(key, value) {
            if (node.name == value.genome) {
                color = value.color;
                return false;
            }
        });

        var genomeCharts = "";
        var colors = [];
        if (node.children.length == 0) {
            colors.push(color);
        } else {
            colors = getGenomeColors(nodeId, []);
        }
        for (var i=0; i<4; i++) {
            var color = "none";
            if (colors.length > i) {
                color = colors[i];
            }
            genomeCharts += "<genome" + i + ">color" + color + "</genome" + i + ">";
        }

        return ""+
            "<clade>"+
                "<name>"+ node.name +"</name>"+
                "<branch_length>1.0</branch_length>"+
                "<annotation>"+
                    "<uri>#"+ nodeId +"</uri>"+
                    "<desc>"+ node.name +"</desc>"+
                "</annotation>"+
                "<chart>"+ genomeCharts +"</chart>"+
            "</clade>";
    }
    $.each(node.children, function (key, value) {
        childrenXml += phyloToXml(value, maxDepth, depth + 1);
    });
    return "<clade><name>" + nodeId + "</name>" + childrenXml + "</clade>";
}

/**
 * Add autocomplete to the search genome field, if you select one, it is selected in the tree
 */
function searchGenome() {
    $("#searchGenome").autocomplete({
        source: genomes,
        minLength: 3,
        select: function( event, ui ) {
            var list = $('#selectedGenomeList ul');
            if (!$('.'+ ui.item.value, list).length) {
                selectedGenomes.push(ui.item.value);
                resizePhyloTree();
                list.append('<li class="genome '+ ui.item.value +'" data-name="'+ ui.item.value +'">'+ ui.item.value +'</li>');
            }
            setTimeout(function() { $('#searchGenome').val(""); }, 500);
        },
        open: function() {
            $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
        },
        close: function() {
            $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
        }
    });
}

/**
 * Select all the genomes beneath a middleNode with given id
 * @param nodeId
 */
function selectMultipleGenomes(nodeId) {
    selectMiddleNode(nodeId);
    selectMultipleGenomesRecursive(nodeId);
}

/**
 * Select all the genomes below the givenMiddleNode recursively
 * @param nodeId
 */
function selectMultipleGenomesRecursive(nodeId) {
    var data = phyloTree[nodeId];
    debugger;
    $.each(data.children, function (key, id) {
        selectMultipleGenomesRecursive(id);
    });

    if (data.children.length == 0) {
        selectGenome(data.name);
    }
}
