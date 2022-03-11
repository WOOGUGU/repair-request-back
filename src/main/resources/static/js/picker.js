// 报修地点数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#locationData'
        , height: 600
        , url: '/v2/picker/selectLocationForBackend'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'area', title: '区域', width: 250}
                , {field: 'position', title: '位置', width: 250}
                , {title: '操作', align: 'center', width: 180, toolbar: '#location'}
            ]
        ]
        , id: 'tableLocation'
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

    $('#searchLocation').on('click', function () {
        table.reload('tableLocation', {
            method: 'get'
            , url: '/v2/picker/selectLocationForBackend'
            , headers: {
                "Cookie": document.cookie
            }
            , where: {
                'pickerId': $('#pickerId').val(),
                'area': $('#area').val(),
                'position': $('#position').val()
            }
            , page: {
                curr: 1
            }
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
    });
});

// 增加报修地点
function addLocation() {
    var area = $("#area").val();
    var position = $("#position").val();
    if (area === "") {
        alert("报修区域不能为空");
        return;
    } else if (position === "") {
        alert("报修位置不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/picker/addPickerLocation',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "area": area,
                "position": position
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

// 修改报修地点
function updateLocation() {
    var pickerId = $("#pickerId").val();
    var area = $("#area").val();
    var position = $("#position").val();
    if (pickerId === "") {
        alert("报修位置id不能为空");
        return;
    } else if (area === "") {
        alert("报修区域不能为空");
        return;
    } else if (position === "") {
        alert("报修位置不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/picker/updatePickerLocation',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "pickerId": pickerId,
                "area": area,
                "position": position
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

// 删除报修地点
function delLocation() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(location)', function (obj) {
                var tr = obj.data;
                var msg = "您真的确定要删除吗？";
                if (confirm(msg) === true) {
                    $.ajax({
                        url: '/v2/picker/deletePickerLocation',
                        type: 'post',
                        headers: {
                            "Cookie": document.cookie
                        },
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

// 跳转到修改报修位置
function toUpdateLocation() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(location)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("pickerId", tr.id);
            window.location.href = "http://localhost:8090/updateLocation.html";
        })
    });
}

// 返回报修位置列表
function backToLocationList() {
    window.location.href = "http://localhost:8090/locationList.html";
}