/**
 * Created by Thomas on 16-06-16.
 */

var phyloColors = [];
var phyloColorList = [];

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
            if (meta.value == "[]") {
                result[meta.key] = [];
                html += '<li><a href="#">'+ meta.key +'</a></li>';
            } else {
                result[meta.key] = meta.value.replace('[', '').replace(']', '').split(', ');
                html += '<li class="opener"><a href="#" style="display: block;">'+ meta.key +'</a><ul class="dropotron">';
                $.each(result[meta.key], function(x, val) {
                    html += '<li><a href="#">'+ val +'</a></li>';
                });
                html += '</ul></li>';
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


    $('#showMetaDataDropdown').find('li').click(function(e) {
        e.preventDefault();
        setPhyloColors($(this).html());
    });
}

function setPhyloColors(metadata) {
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
        resizePhyloTree();
    });
}