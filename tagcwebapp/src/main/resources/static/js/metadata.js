/**
 * Created by Thomas on 16-06-16.
 */

var phyloColorList = [];
var metaData = {};
var activeMeta = "";

$('document').ready(function() {
    loadMetaData();
});

function loadMetaData() {
    $.ajax({
        url: url + 'api/getmetadatamap',
        dataType: 'JSON',
        type: 'GET'
    }).done(function (data) {
        var result = {};
        var html = "";
        $.each(data.list.entry, function(key, meta) {
            html += '<li><a href="#" onclick="setPhyloColors(\''+ meta.key +'\')">'+ meta.key +'</a></li>';
            if (meta.value == "[]") {
                result[meta.key] = [];
            } else {
                result[meta.key] = meta.value.replace('[', '').replace(']', '').split(', ');
            }
        });
        $('#showMetaDataDropdown').find('ul').html(html);
        $('#nav > ul').dropotron({
            offsetY: -15,
            hoverDelay: 0,
            alignment: 'center'
        });
        metaData = result;
        setPhyloColors("lineage");
    });

}

function setPhyloColors(metadata) {
    activeMeta = metadata;
    $.ajax({
        url: url + 'api/getgenomecolors',
        dataType: 'JSON',
        type: 'GET',
        data: { metadata: metadata }
    }).done(function (data) {
        phyloColors = [];
        phyloColorList = [];
        $.each(data.list.entry, function(key, meta) {
            phyloColors.push({
                'genome': meta.key,
                'color': meta.value
            });
            var index = $.inArray(meta.value, phyloColorList);
            if (index == -1) {
                phyloColorList.push(meta.value);
            }
        });
        drawPhyloLegenda();
        resizePhyloTree();
    });
}

function getPhyloColorStyles() {
    var result = "";
    $.each(phyloColorList, function(key, color) {
        result += '<color'+ color +' fill="#'+ color +'" stroke="#'+ color +'"/>';
    });
    result += '<none fill="#FFF" stroke="#CCC" />';
    return result;
}

function drawPhyloLegenda() {
    var legenda = $('#phyloColorLegenda');
    $.each(metaData[activeMeta], function(key, color) {
        legenda.append('<li style="background-color: "#'+ color.color +'">'+ color.name +'</li>');
    });
}