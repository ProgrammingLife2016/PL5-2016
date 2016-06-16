/**
 * Created by Thomas on 09-06-16.
 */
var phyloTree = {};
var phyloRoot;
var currentNode = -1;
var back = [];
var selectedGenomes = [];
var selectedMiddleNodes = [];
var lineages = [];

$("document").ready(function () {
    //Set the global variable containing all the lineages mapped to the genomes
    setLineages();

    //Load the phylogenic Tree
    $.ajax({
        url: url + "api/getphylogenetictree?treeId=1",
        dataType: "json",
        type: "get"
    }).done(function (data) {
        data.id = -1;
        parsePhyloTree(data);
        drawPhyloTree(-1);
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

        fullSizeMinimap();

        var names = selectedGenomes.concat(selectedMiddleNodes);

        $.ajax({

            url : url + 'api/setactivegenomes',
            dataType : 'JSON',
            type : 'POST',
            data : { 'names': names }
        }).done(function(respData) {
            initializeMinimap();
        });
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

    $('body').on('click', 'svg circle', function () {
        var nodeId = $(this).attr('nodeid');
        selectMiddleNode(nodeId);
    });

    $('body').on('click', '#selectedGenomeList li:not(".firstLi")', function() {
        if ($(this).hasClass('genome')) {
            selectGenome($(this).data('name'));
        } else {
            selectMiddleNode($(this).data('id'));
        }
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
        $('#selectedGenomeList').append('<li class="genome '+ genome +'" data-name="'+ genome +'">'+ genome +'</li>');
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
        $('#selectedGenomeList').append('<li class="middleNode '+ nodeId +'" data-id="'+ nodeId +'">MiddleNode '+ nodeId +'</li>');
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
    var data = '<phyloxml xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.phyloxml.org http://www.phyloxml.org/1.10/phyloxml.xsd" xmlns="http://www.phyloxml.org"><phylogeny rooted="false">';
    data += '<render><parameters><circular><bufferRadius>0.5</bufferRadius></circular><rectangular><alignRight>1</alignRight></rectangular></parameters><charts><genome type="binary" thickness="10"/><heatmap type="binary" thickness="10" disjointed="1" bufferSiblings="0.3"/></charts><styles><lin1 fill="#ed00c3" stroke="#000000"/><lin2 fill="#0000ff" stroke="#000000"/><lin3 fill="#500079" stroke="#000000"/><lin4 fill="#ff0000" stroke="#000000"/><none fill="#FFF" stroke="#CCC" /><heat1 fill="#00FF00"></heat1><heat2 fill="#11EE00"></heat2><heat3 fill="#22DD00"></heat3><heat4 fill="#33CC00"></heat4><heat5 fill="#44BB00"></heat5><heat6 fill="#55AA00"></heat6><heat7 fill="#669900"></heat7><heat8 fill="#778800"></heat8><heat9 fill="#887700"></heat9><heat10 fill="#996600"></heat10><heat11 fill="#AA5500"></heat11><heat12 fill="#BB4400"></heat12><heat13 fill="#CC3300"></heat13><heat14 fill="#DD2200"></heat14><heat15 fill="#EE1100"></heat15><heat16 fill="#FF0000"></heat16></styles></render>';
    data += phyloToXml(root, 6, 0);
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

    $('svg circle').each(function (key, value) {
        if ($.inArray($(value).attr('nodeid'), selectedMiddleNodes) > -1) {
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
        var heatmap = (node.count > 1) ? "<heatmap>heat" + heat + "</heatmap>" : "<heatmap>none</heatmap>";
        lineage = 'none';
        $.each(lineages, function (key, value) {
            if (node.name == value.genome) {
                lineage = value.lineage;
                return false;
            }
        });
        return "" +
            "<clade>" +
            "<name>" + node.name + "</name>" +
            "<branch_length>1.0</branch_length>" +
            "<annotation>" +
            "<uri>#" + nodeId + "</uri>" +
            "<desc>" + node.name + "</desc>" +
            "</annotation>" +
            "<chart>" +
            "<genome>" + lineage + "</genome>" +
            heatmap +
            "</chart>" +
            "</clade>";
    }
    $.each(node.children, function (key, value) {
        childrenXml += phyloToXml(value, maxDepth, depth + 1);
    });
    return "<clade><name>" + nodeId + "</name>" + childrenXml + "</clade>";
}

/**
 * Temp: TODO: function to set all the lineages mapped to the genomes, should be done through the REST API.
 */
function setLineages() {
    lineages = [{'genome': "TKK_03_0042", 'lineage': "lin4"},
        {'genome': "TKK_03_0160", 'lineage': "lin2"},
        {'genome': "TKK_03_0040", 'lineage': "lin2"},
        {'genome': "TKK-01-0039", 'lineage': "lin2"},
        {'genome': "TKK-01-0038", 'lineage': "lin4"},
        {'genome': "TKK-01-0031", 'lineage': "lin4"},
        {'genome': "TKK-01-0030", 'lineage': "lin4"},
        {'genome': "TKK-01-0033", 'lineage': "lin4"},
        {'genome': "TKK_03_0047", 'lineage': "lin2"},
        {'genome': "TKK-01-0032", 'lineage': "lin4"},
        {'genome': "TKK-01-0035", 'lineage': "lin4"},
        {'genome': "TKK_03_0045", 'lineage': "lin2"},
        {'genome': "TKK-01-0034", 'lineage': "lin4"},
        {'genome': "TKK-01-0037", 'lineage': "lin4"},
        {'genome': "TKK_03_0043", 'lineage': "lin2"},
        {'genome': "TKK-01-0036", 'lineage': "lin4"},
        {'genome': "TKK_03_0044", 'lineage': "lin4"},
        {'genome': "TKK_03_0030", 'lineage': "lin4"},
        {'genome': "TKK_03_0031", 'lineage': "lin2"},
        {'genome': "TKK_03_0150", 'lineage': "lin4"},
        {'genome': "TKK-01-0040", 'lineage': "lin4"},
        {'genome': "TKK-01-0049", 'lineage': "lin4"},
        {'genome': "TKK-01-0042", 'lineage': "lin4"},
        {'genome': "TKK_03_0039", 'lineage': "lin2"},
        {'genome': "TKK-01-0041", 'lineage': "lin4"},
        {'genome': "TKK-01-0044", 'lineage': "lin4"},
        {'genome': "TKK_03_0036", 'lineage': "lin4"},
        {'genome': "TKK_03_0037", 'lineage': "lin3"},
        {'genome': "TKK_03_0158", 'lineage': "lin4"},
        {'genome': "TKK-01-0043", 'lineage': "lin2"},
        {'genome': "TKK_03_0034", 'lineage': "lin2"},
        {'genome': "TKK-01-0046", 'lineage': "lin2"},
        {'genome': "TKK_03_0035", 'lineage': "lin4"},
        {'genome': "TKK-01-0045", 'lineage': "lin4"},
        {'genome': "TKK_03_0156", 'lineage': "lin2"},
        {'genome': "TKK-01-0048", 'lineage': "lin4"},
        {'genome': "TKK_03_0153", 'lineage': "lin4"},
        {'genome': "TKK-01-0047", 'lineage': "lin2"},
        {'genome': "TKK_03_0033", 'lineage': "lin3"},
        {'genome': "TKK_03_0154", 'lineage': "lin2"},
        {'genome': "TKK_03_0063", 'lineage': "lin4"},
        {'genome': "TKK_03_0064", 'lineage': "lin4"},
        {'genome': "TKK-01-0050", 'lineage': "lin4"},
        {'genome': "TKK-01-0053", 'lineage': "lin4"},
        {'genome': "TKK-01-0052", 'lineage': "lin4"},
        {'genome': "TKK-01-0055", 'lineage': "lin2"},
        {'genome': "TKK-01-0054", 'lineage': "lin4"},
        {'genome': "TKK-01-0057", 'lineage': "lin4"},
        {'genome': "TKK-01-0056", 'lineage': "lin2"},
        {'genome': "TKK_05MA_0009", 'lineage': "lin4"},
        {'genome': "TKK_03_0065", 'lineage': "lin2"},
        {'genome': "TKK-01-0058", 'lineage': "lin4"},
        {'genome': "TKK_03_0050", 'lineage': "lin4"},
        {'genome': "TKK-01-0060", 'lineage': "lin4"},
        {'genome': "TKK_05MA_0004", 'lineage': "lin4"},
        {'genome': "TKK-01-0062", 'lineage': "lin2"},
        {'genome': "TKK-01-0061", 'lineage': "lin4"},
        {'genome': "TKK_02_0080", 'lineage': "lin2"},
        {'genome': "TKK-01-0064", 'lineage': "lin4"},
        {'genome': "TKK-01-0063", 'lineage': "lin4"},
        {'genome': "TKK-01-0066", 'lineage': "lin4"},
        {'genome': "TKK_03_0058", 'lineage': "lin2"},
        {'genome': "TKK_03_0059", 'lineage': "lin4"},
        {'genome': "TKK-01-0065", 'lineage': "lin2"},
        {'genome': "TKK-01-0068", 'lineage': "lin4"},
        {'genome': "TKK-01-0067", 'lineage': "lin2"},
        {'genome': "TKK-01-0069", 'lineage': "lin4"},
        {'genome': "TKK_05MA_0033", 'lineage': "lin4"},
        {'genome': "TKK_05MA_0037", 'lineage': "lin4"},
        {'genome': "TKK_05MA_0035", 'lineage': "lin4"},
        {'genome': "TKK_02_0076", 'lineage': "lin4"},
        {'genome': "TKK_04_0131", 'lineage': "lin2"},
        {'genome': "TKK_04_0130", 'lineage': "lin4"},
        {'genome': "TKK_02_0079", 'lineage': "lin4"},
        {'genome': "TKK_04_0014", 'lineage': "lin2"},
        {'genome': "TKK_04_0134", 'lineage': "lin2"},
        {'genome': "TKK_04_0132", 'lineage': "lin4"},
        {'genome': "TKK_04_0139", 'lineage': "lin3"},
        {'genome': "TKK_04_0018", 'lineage': "lin2"},
        {'genome': "TKK_02_0071", 'lineage': "lin4"},
        {'genome': "TKK_04_0017", 'lineage': "lin2"},
        {'genome': "TKK_04_0137", 'lineage': "lin4"},
        {'genome': "TKK_02_0074", 'lineage': "lin4"},
        {'genome': "TKK_02_0073", 'lineage': "lin4"},
        {'genome': "TKK_04_0015", 'lineage': "lin4"},
        {'genome': "TKK_04_0136", 'lineage': "lin2"},
        {'genome': "TKK_04_0019", 'lineage': "lin2"},
        {'genome': "TKK_02_0065", 'lineage': "lin4"},
        {'genome': "TKK_02_0067", 'lineage': "lin4"},
        {'genome': "TKK_02_0066", 'lineage': "lin4"},
        {'genome': "TKK_04_0120", 'lineage': "lin1"},
        {'genome': "TKK_02_0068", 'lineage': "lin4"},
        {'genome': "TKK_04_0124", 'lineage': "lin4"},
        {'genome': "TKK-01-0006", 'lineage': "lin4"},
        {'genome': "TKK_04_0003", 'lineage': "lin1"},
        {'genome': "TKK_04_0123", 'lineage': "lin4"},
        {'genome': "TKK_04_0002", 'lineage': "lin4"},
        {'genome': "TKK-01-0005", 'lineage': "lin2"},
        {'genome': "TKK_04_0122", 'lineage': "lin4"},
        {'genome': "TKK_04_0001", 'lineage': "lin2"},
        {'genome': "TKK-01-0008", 'lineage': "lin2"},
        {'genome': "TKK-01-0007", 'lineage': "lin4"},
        {'genome': "TKK_04_0007", 'lineage': "lin4"},
        {'genome': "TKK_02_0060", 'lineage': "lin4"},
        {'genome': "TKK_04_0006", 'lineage': "lin2"},
        {'genome': "TKK-01-0009", 'lineage': "lin4"},
        {'genome': "TKK_04_0005", 'lineage': "lin1"},
        {'genome': "TKK_04_0126", 'lineage': "lin2"},
        {'genome': "TKK_02_0062", 'lineage': "lin3"},
        {'genome': "TKK_04_0125", 'lineage': "lin4"},
        {'genome': "TKK_03_0118", 'lineage': "lin2"},
        {'genome': "TKK_03_0115", 'lineage': "lin4"},
        {'genome': "TKK_04_0129", 'lineage': "lin4"},
        {'genome': "TKK_04_0008", 'lineage': "lin4"},
        {'genome': "TKK_03_0114", 'lineage': "lin2"},
        {'genome': "TKK-01-0002", 'lineage': "lin2"},
        {'genome': "TKK_03_0111", 'lineage': "lin2"},
        {'genome': "TKK-01-0001", 'lineage': "lin4"},
        {'genome': "TKK_03_0112", 'lineage': "lin2"},
        {'genome': "TKK-01-0004", 'lineage': "lin4"},
        {'genome': "TKK-01-0003", 'lineage': "lin2"},
        {'genome': "TKK_03_0020", 'lineage': "lin4"},
        {'genome': "TKK_02_0053", 'lineage': "lin4"},
        {'genome': "TKK_02_0056", 'lineage': "lin4"},
        {'genome': "TKK_02_0055", 'lineage': "lin2"},
        {'genome': "TKK_02_0058", 'lineage': "lin4"},
        {'genome': "TKK_05MA_0051", 'lineage': "lin4"},
        {'genome': "TKK_04_0113", 'lineage': "lin4"},
        {'genome': "TKK-01-0017", 'lineage': "lin4"},
        {'genome': "TKK_04_0112", 'lineage': "lin4"},
        {'genome': "TKK-01-0016", 'lineage': "lin4"},
        {'genome': "TKK-01-0019", 'lineage': "lin4"},
        {'genome': "TKK-01-0018", 'lineage': "lin3"},
        {'genome': "TKK_04_0117", 'lineage': "lin4"},
        {'genome': "TKK_02_0050", 'lineage': "lin4"},
        {'genome': "TKK_02_0052", 'lineage': "lin4"},
        {'genome': "TKK_03_0029", 'lineage': "lin1"},
        {'genome': "TKK_02_0051", 'lineage': "lin4"},
        {'genome': "TKK_04_0114", 'lineage': "lin2"},
        {'genome': "TKK_03_0027", 'lineage': "lin2"},
        {'genome': "TKK_03_0149", 'lineage': "lin2"},
        {'genome': "TKK_03_0028", 'lineage': "lin2"},
        {'genome': "TKK-01-0011", 'lineage': "lin4"},
        {'genome': "TKK_03_0025", 'lineage': "lin2"},
        {'genome': "TKK_04_0118", 'lineage': "lin4"},
        {'genome': "TKK_03_0026", 'lineage': "lin4"},
        {'genome': "TKK-01-0010", 'lineage': "lin4"},
        {'genome': "TKK_03_0023", 'lineage': "lin4"},
        {'genome': "TKK-01-0013", 'lineage': "lin2"},
        {'genome': "TKK-01-0012", 'lineage': "lin4"},
        {'genome': "TKK_03_0024", 'lineage': "lin4"},
        {'genome': "TKK-01-0015", 'lineage': "lin4"},
        {'genome': "TKK-01-0014", 'lineage': "lin4"},
        {'genome': "TKK_02_0043", 'lineage': "lin4"},
        {'genome': "TKK_02_0042", 'lineage': "lin4"},
        {'genome': "TKK_02_0044", 'lineage': "lin4"},
        {'genome': "TKK_02_0047", 'lineage': "lin4"},
        {'genome': "TKK_05MA_0040", 'lineage': "lin4"},
        {'genome': "TKK_02_0046", 'lineage': "lin2"},
        {'genome': "TKK_02_0049", 'lineage': "lin4"},
        {'genome': "TKK_02_0048", 'lineage': "lin4"},
        {'genome': "TKK-01-0028", 'lineage': "lin4"},
        {'genome': "TKK-01-0027", 'lineage': "lin2"},
        {'genome': "TKK-01-0029", 'lineage': "lin4"},
        {'genome': "TKK_04_0106", 'lineage': "lin4"},
        {'genome': "TKK_04_0105", 'lineage': "lin4"},
        {'genome': "TKK_04_0104", 'lineage': "lin4"},
        {'genome': "TKK_02_0041", 'lineage': "lin4"},
        {'genome': "TKK_02_0040", 'lineage': "lin4"},
        {'genome': "TKK_04_0103", 'lineage': "lin4"},
        {'genome': "TKK-01-0020", 'lineage': "lin4"},
        {'genome': "TKK_04_0109", 'lineage': "lin4"},
        {'genome': "TKK_04_0108", 'lineage': "lin4"},
        {'genome': "TKK-01-0022", 'lineage': "lin4"},
        {'genome': "TKK_03_0015", 'lineage': "lin4"},
        {'genome': "TKK_04_0107", 'lineage': "lin2"},
        {'genome': "TKK-01-0021", 'lineage': "lin2"},
        {'genome': "TKK-01-0024", 'lineage': "lin2"},
        {'genome': "TKK-01-0026", 'lineage': "lin4"},
        {'genome': "TKK-01-0025", 'lineage': "lin4"},
        {'genome': "TKK_05MA_2015", 'lineage': "lin4"},
        {'genome': "TKK_02_0031", 'lineage': "lin4"},
        {'genome': "TKK_02_0036", 'lineage': "lin4"},
        {'genome': "TKK_04_0054", 'lineage': "lin2"},
        {'genome': "TKK_02_0035", 'lineage': "lin4"},
        {'genome': "TKK_02_0038", 'lineage': "lin4"},
        {'genome': "TKK_04_0051", 'lineage': "lin4"},
        {'genome': "TKK_02_0037", 'lineage': "lin4"},
        {'genome': "TKK_02_0030", 'lineage': "lin4"},
        {'genome': "TKK_04_0059", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0055", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0054", 'lineage': "lin4"},
        {'genome': "TKK_02_0028", 'lineage': "lin4"},
        {'genome': "TKK_05MA_2005", 'lineage': "lin4"},
        {'genome': "TKK_02_0021", 'lineage': "lin4"},
        {'genome': "TKK_02_0020", 'lineage': "lin4"},
        {'genome': "TKK_02_0023", 'lineage': "lin4"},
        {'genome': "TKK_02_0025", 'lineage': "lin4"},
        {'genome': "TKK_02_0024", 'lineage': "lin4"},
        {'genome': "TKK_04_0042", 'lineage': "lin4"},
        {'genome': "TKK_02_0027", 'lineage': "lin4"},
        {'genome': "TKK_04_0040", 'lineage': "lin4"},
        {'genome': "TKK_04_0047", 'lineage': "lin4"},
        {'genome': "TKK_04_0046", 'lineage': "lin4"},
        {'genome': "TKK_04_0045", 'lineage': "lin4"},
        {'genome': "TKK_04_0044", 'lineage': "lin4"},
        {'genome': "TKK_04_0048", 'lineage': "lin4"},
        {'genome': "TKK_02_0018", 'lineage': "lin4"},
        {'genome': "TKK_02_0010", 'lineage': "lin2"},
        {'genome': "TKK_02_0012", 'lineage': "lin4"},
        {'genome': "TKK_02_0014", 'lineage': "lin4"},
        {'genome': "TKK_04_0153", 'lineage': "lin4"},
        {'genome': "TKK_04_0031", 'lineage': "lin4"},
        {'genome': "TKK_02_0016", 'lineage': "lin4"},
        {'genome': "TKK_04_0030", 'lineage': "lin4"},
        {'genome': "TKK_04_0150", 'lineage': "lin4"},
        {'genome': "TKK_04_0157", 'lineage': "lin4"},
        {'genome': "TKK_04_0036", 'lineage': "lin4"},
        {'genome': "TKK_04_0155", 'lineage': "lin4"},
        {'genome': "TKK_04_0034", 'lineage': "lin4"},
        {'genome': "TKK_04_0033", 'lineage': "lin2"},
        {'genome': "TKK_03_0108", 'lineage': "lin4"},
        {'genome': "TKK_03_0109", 'lineage': "lin2"},
        {'genome': "TKK_04_0038", 'lineage': "lin4"},
        {'genome': "TKK_04_0159", 'lineage': "lin2"},
        {'genome': "TKK_04_0158", 'lineage': "lin4"},
        {'genome': "TKK_04_0037", 'lineage': "lin4"},
        {'genome': "TKK_03_0105", 'lineage': "lin4"},
        {'genome': "TKK_03_0102", 'lineage': "lin4"},
        {'genome': "TKK_03_0103", 'lineage': "lin4"},
        {'genome': "TKK_03_0100", 'lineage': "lin2"},
        {'genome': "TKK_03_0101", 'lineage': "lin4"},
        {'genome': "TKK_02_0007", 'lineage': "lin4"},
        {'genome': "TKK_02_0006", 'lineage': "lin4"},
        {'genome': "TKK_02_0008", 'lineage': "lin4"},
        {'genome': "TKK_02_0001", 'lineage': "lin2"},
        {'genome': "TKK_04_0021", 'lineage': "lin4"},
        {'genome': "TKK_04_0020", 'lineage': "lin1"},
        {'genome': "TKK_04_0141", 'lineage': "lin4"},
        {'genome': "TKK_02_0002", 'lineage': "lin2"},
        {'genome': "TKK_04_0140", 'lineage': "lin4"},
        {'genome': "TKK_02_0005", 'lineage': "lin4"},
        {'genome': "TKK_02_0004", 'lineage': "lin4"},
        {'genome': "TKK_04_0024", 'lineage': "lin4"},
        {'genome': "TKK_04_0145", 'lineage': "lin4"},
        {'genome': "TKK_04_0023", 'lineage': "lin2"},
        {'genome': "TKK_04_0022", 'lineage': "lin4"},
        {'genome': "TKK_04_0149", 'lineage': "lin2"},
        {'genome': "TKK_04_0148", 'lineage': "lin3"},
        {'genome': "TKK_05SA_0020", 'lineage': "lin4"},
        {'genome': "TKK_03_0083", 'lineage': "lin2"},
        {'genome': "TKK_04_0090", 'lineage': "lin4"},
        {'genome': "TKK-01-0071", 'lineage': "lin4"},
        {'genome': "TKK_03_0082", 'lineage': "lin4"},
        {'genome': "TKK-01-0070", 'lineage': "lin1"},
        {'genome': "TKK-01-0073", 'lineage': "lin4"},
        {'genome': "TKK_04_0094", 'lineage': "lin4"},
        {'genome': "TKK_04_0098", 'lineage': "lin2"},
        {'genome': "TKK_04_0097", 'lineage': "lin4"},
        {'genome': "TKK_04_0096", 'lineage': "lin4"},
        {'genome': "TKK_04_0095", 'lineage': "lin4"},
        {'genome': "TKK_04_0099", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0019", 'lineage': "lin2"},
        {'genome': "TKK_05SA_0018", 'lineage': "lin4"},
        {'genome': "TKK-01-0075", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0016", 'lineage': "lin4"},
        {'genome': "TKK-01-0074", 'lineage': "lin4"},
        {'genome': "TKK-01-0077", 'lineage': "lin4"},
        {'genome': "TKK-01-0076", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0014", 'lineage': "lin4"},
        {'genome': "TKK_03_0089", 'lineage': "lin4"},
        {'genome': "TKK-01-0079", 'lineage': "lin2"},
        {'genome': "TKK-01-0078", 'lineage': "lin2"},
        {'genome': "TKK_05SA_0011", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0010", 'lineage': "lin4"},
        {'genome': "TKK_03_0075", 'lineage': "lin2"},
        {'genome': "TKK-01-0080", 'lineage': "lin1"},
        {'genome': "TKK_03_0072", 'lineage': "lin2"},
        {'genome': "TKK-01-0082", 'lineage': "lin2"},
        {'genome': "TKK-01-0081", 'lineage': "lin4"},
        {'genome': "TKK-01-0084", 'lineage': "lin4"},
        {'genome': "TKK-01-0083", 'lineage': "lin4"},
        {'genome': "TKK_04_0083", 'lineage': "lin4"},
        {'genome': "TKK_04_0082", 'lineage': "lin4"},
        {'genome': "TKK_04_0081", 'lineage': "lin4"},
        {'genome': "TKK_04_0080", 'lineage': "lin1"},
        {'genome': "TKK_04_0086", 'lineage': "lin2"},
        {'genome': "TKK_04_0085", 'lineage': "lin1"},
        {'genome': "TKK_04_0084", 'lineage': "lin2"},
        {'genome': "TKK_04_0089", 'lineage': "lin2"},
        {'genome': "TKK-01-0085", 'lineage': "lin4"},
        {'genome': "TKK-01-0088", 'lineage': "lin2"},
        {'genome': "TKK_05SA_0025", 'lineage': "lin4"},
        {'genome': "TKK-01-0087", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0024", 'lineage': "lin4"},
        {'genome': "TKK_03_0078", 'lineage': "lin2"},
        {'genome': "TKK-01-0089", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0021", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0042", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0041", 'lineage': "lin4"},
        {'genome': "TKK-01-0091", 'lineage': "lin4"},
        {'genome': "TKK-01-0090", 'lineage': "lin4"},
        {'genome': "TKK-01-0093", 'lineage': "lin2"},
        {'genome': "TKK-01-0092", 'lineage': "lin2"},
        {'genome': "TKK-01-0094", 'lineage': "lin2"},
        {'genome': "TKK_04_0071", 'lineage': "lin4"},
        {'genome': "TKK_04_0070", 'lineage': "lin4"},
        {'genome': "TKK_04_0075", 'lineage': "lin4"},
        {'genome': "TKK_04_0074", 'lineage': "lin4"},
        {'genome': "TKK_04_0078", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0052", 'lineage': "lin2"},
        {'genome': "TKK_03_0094", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0050", 'lineage': "lin3"},
        {'genome': "TKK_03_0092", 'lineage': "lin4"},
        {'genome': "TKK_03_0090", 'lineage': "lin2"},
        {'genome': "TKK_04_0061", 'lineage': "lin4"},
        {'genome': "TKK_04_0064", 'lineage': "lin4"},
        {'genome': "TKK_04_0062", 'lineage': "lin4"},
        {'genome': "TKK_04_0069", 'lineage': "lin4"},
        {'genome': "TKK_04_0068", 'lineage': "lin4"},
        {'genome': "TKK_04_0067", 'lineage': "lin4"},
        {'genome': "TKK_04_0066", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0048", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0046", 'lineage': "lin2"},
        {'genome': "TKK_03_0098", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0044", 'lineage': "lin4"},
        {'genome': "TKK_05SA_0043", 'lineage': "lin4"},
        {'genome': "TKK_03_0099", 'lineage': "lin4"}];
}