// 故障描述数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#desData'
        , height: 600
        , url: '/v2/picker/selectAllPickerDes'
        , method: 'get'
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'picker', title: '故障描述', width: 250}
                , {title: '操作', align: 'center', width: 180, toolbar: '#des'}
            ]
        ]
        , id: 'tableDes'
        , toolbar: '#toolbar'
        , page: true //开启分页
        , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
        , limit: 10  //一页显示10条数据
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
            if (res.data.length == 1) {
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": res.data //解析数据列表
                };
            } else {
                var result;
                if (this.page.curr) {
                    result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
                } else {
                    result = res.data.slice(0, this.limit);
                }
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": res.data.length, //解析数据长度
                    "data": result //解析数据列表
                };
            }
        }
    });
    //
    // $('#searchDes').on('click', function () {
    //     table.reload('tableTime', {
    //         method: 'get'
    //         , url: '/v2/picker/selectLocationForBackend'
    //         , headers: {
    //             "Cookie": document.cookie
    //         }
    //         , where: {
    //             'pickerId': $('#pickerId').val(),
    //             'area': $('#area').val(),
    //             'position': $('#position').val()
    //         }
    //         , page: {
    //             curr: 1
    //         }
    //         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
    //             if (res.data.length == 1) {
    //                 return {
    //                     "code": 0, //解析接口状态
    //                     "msg": "ok", //解析提示文本
    //                     "count": 1, //解析数据长度
    //                     "data": res.data //解析数据列表
    //                 };
    //             } else {
    //                 var result;
    //                 if (this.page.curr) {
    //                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
    //                 } else {
    //                     result = res.data.slice(0, this.limit);
    //                 }
    //                 return {
    //                     "code": 0, //解析接口状态
    //                     "msg": "ok", //解析提示文本
    //                     "count": res.data.length, //解析数据长度
    //                     "data": result //解析数据列表
    //                 };
    //             }
    //         }
    //     });
    // });
});

// 增加故障描述
function addDes() {
    var des = $("#des").val();
    if (des === "") {
        alert("故障描述不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/picker/addPicker',
            type: 'post',
            data: {
                "type": 'des',
                "value": des
            },
            success: function (res) {
                if (res.userMsg !== "") {
                    alert(res.userMsg);
                } else {
                    alert("发生未知错误，请重试");
                }
            },
            error: function () {
                alert("发生未知错误，请重试");
            }
        });
    }
}

// 修改故障描述
function updateDes() {
    var pickerId = $("#pickerId").val();
    var des = $("#des").val();
    if (pickerId === "") {
        alert("报修时间id不能为空");
        return;
    } else if (des === "") {
        alert("故障描述不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/picker/updatePicker',
            type: 'post',
            data: {
                "pickerId": pickerId,
                "type": 'des',
                "value": des
            },
            success: function (res) {
                if (res.userMsg !== "") {
                    alert(res.userMsg);
                } else {
                    alert("发生未知错误，请重试");
                }
            },
            error: function () {
                alert("发生未知错误，请重试");
            }
        });
    }
}

// 删除故障描述
function delDes() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(des)', function (obj) {
                var tr = obj.data;
                var msg = "您真的确定要删除吗？";
                if (confirm(msg) === true) {
                    $.ajax({
                        url: '/v2/picker/deletePickerById',
                        type: 'post',
                        data: {
                            "pickerId": tr.id
                        },
                        success: function (res) {
                            if (res.userMsg !== "") {
                                alert(res.userMsg);
                                location.reload();
                            } else {
                                alert("发生未知错误，请重试");
                            }
                        },
                        error: function () {
                            alert("发生未知错误，请重试");
                        }
                    });
                } else {
                    return false;
                }
            })
        });
    })
}

// 跳转到修改故障描述
function toUpdateDes() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(des)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("desId", tr.id);
            window.location.href = "/updateDes.html";
        })
    });
}

// 返回故障描述列表
function backToDesList() {
    window.location.href = "/desList.html";
}