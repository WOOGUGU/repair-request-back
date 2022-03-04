// 通知数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#noticeData'
        , height: 600
        , url: '/v2/notice/selectAllNotice'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'content', title: '公告网址', width: 250}
                , {field: 'author', title: '发布者', width: 250}
                , {field: 'createTime', title: '创建时间', width: 250}
                , {field: 'announceTime', title: '发布时间', width: 250}
                , {field: 'updateTime', title: '修改时间', width: 250}
                , {
                field: 'displayStatus', title: '状态', width: 250, templet: function (d) {
                    return d.displayStatus === 1 ? '展示' : '隐藏';
                }
            }
                , {title: '操作', align: 'center', width: 180, toolbar: '#notice'}
            ]
        ]
        , id: 'tableSeven'
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

    $('#searchNotice').on('click', function () {
        table.reload('tableSeven', {
            method: 'get'
            , url: '/v2/notice/selectNotice'
            , headers: {
                "Cookie": document.cookie
            }
            , where: {
                'noticeId': $('#noticeId').val(),
                'author': $('#author').val(),
                'displayStatus': $('#displayStatus').val()
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

// 增加通知
function addNotice() {
    var content = $("#content").val();
    var author = $("#author").val();
    if (content === "" || content === "") {
        alert("通知网址不能为空");
        return;
    } else if (author === "") {
        alert("发布者不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/notice/addNotice',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "content": content,
                "author": author,
                "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
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

// 删除通知
function delNotice() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(notice)', function (obj) {
                var tr = obj.data;
                var msg = "您真的确定要删除吗？";
                if (confirm(msg) === true) {
                    $.ajax({
                        url: '/v2/notice/deleteNotice',
                        type: 'post',
                        headers: {
                            "Cookie": document.cookie
                        },
                        data: {
                            "noticeId": tr.id
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

// 修改通知
function updateNotice() {
    var noticeId = $("#noticeId").val();
    var content = $("#content").val();
    var author = $("#author").val();
    if (noticeId === "") {
        alert("通知Id不能为空");
        return;
    } else if (content === "") {
        alert("通知网址不能为空");
        return;
    } else if (author === "") {
        alert("发布者不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/notice/updateNotice',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "noticeId": noticeId,
                "content": content,
                "author": author,
                "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
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

// 返回通知列表
function backToNoticeList() {
    window.location.href = "http://localhost:8090/noticeList.html";
}

// 跳转到修改通知页面
function toUpdateNotice() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(notice)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("noticeId", tr.id);
            window.location.href = "http://localhost:8090/updateNotice.html";
        })
    });
}