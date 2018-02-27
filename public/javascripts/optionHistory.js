$(
function() {


    var data = [ {
        title: "No",
        data: "historyId",
        "class" : "fieldHistoryId"
    }, {
        title: "日時",
        data: "createTime",
        "class" : "fieldCreateTime"
    }, {
        title: "担当者",
        data: "userName",
        "class" : "fieldUserName"
    }, {
        title: "アカウントID",
        data: "coAccountIds",
        "class" : "fieldCoAccountIds"
    }, {
        title: "URLグループID",
        data: "urlGroupIds",
        "class" : "fieldUrlGroupIds"
    }, {
        title: "ターゲティングタイプ",
        data: "targetingType",
        "class" : "fieldTargetingType"
    }, {
        title: "ストラクトID",
        data: "structIds",
        "class" : "fieldStructIds"
    }, {
        title: "デバイス",
        data: "platform",
        "class" : "fieldPlatform"
    }, {
        title: "登録単位",
        data: "targetUnit",
        "class" : "fieldTargetUnit"
    }, {
        title: "登録項目",
        data: "entryTypeName",
        "class" : "fieldEntryType"
    }, {
        title: "機能選択",
        data: "actionType",
        "class" : "fieldActionType"
    }, {
        title: "登録方法",
        data: "registMethod",
        "class" : "fieldRegistMethod"
    }, {
        title: "登録ファイル",
        data: "uploadFileName",
        "class" : "fieldUploadFileName"
    }, {
        title: "登録内容確認",
        data: "confirmFileName",
        "class" : "fieldConfirmFileName"
    }  ];

    var $table = $("#p021SspPubSiteHistoryList");
    $table.addClass("dataTable no-footer");
    var $thead = $("<thead></thead>");
    $table.append($thead);
    var $tbody = $("<tbody></tboey>");
    $table.append($tbody);

    var $trTemp = $("<tr role='row' class='odd'></tr>");
    for( var i = 0; i < data.length; i++ ) {
        var $tdTemp = $("<th>"+ data[i].title +"</th>");
        $trTemp.append($tdTemp);
        $tdTemp.addClass("sorting_disabled");
        $tdTemp.addClass(data[i].class);
    }
    $thead.append($trTemp);


    $.getJSON("/getOptionHistoryList/", function(json) {
        for (var i = 0; i < json.length; i++) {
            $tbody.append("<tr><td class='fieldHistoryId'>" + json[i].historyId + "</td><td class='fieldCreateTime'>" +
            json[i].createTime + "</td><td class='fieldUserName'>" +
            json[i].userName + "</td><td class='fieldCoAccountIds'>" +
            json[i].coAccountIds + "</td><td class='fieldUrlGroupIds'>" +
            json[i].urlGroupIds + "</td><td class='fieldTargetingType'>" +
            json[i].targetingType + "</td><td class='fieldStructIds'>" +
            json[i].structIds + "</td><td class='fieldPlatform'>" +
            json[i].platform + "</td><td class='fieldTargetUnit'>" +
            json[i].targetUnit + "</td><td class='fieldEntryType'>" +
            json[i].entryTypeName + "</td><td class='fieldActionType'>" +
            json[i].actionType + "</td><td class='fieldRegistMethod'>" +
            json[i].registMethod + "</td><td class='fieldUploadFileName'>" +
            json[i].uploadFileName + "</td><td class='fieldConfirmFileName'>" +
            json[i].confirmFileName + "</td></tr>");
        }
    });
}
);