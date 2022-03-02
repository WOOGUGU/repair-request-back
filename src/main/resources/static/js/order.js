// 工单数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#orderData'
        , height: 600
        , url: '/v2/order/selectAllOrder'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'sender', title: '发起者姓名', width: 250}
                , {field: 'username', title: '用户名', width: 250}
                , {field: 'tel', title: '联系方式', width: 250}
                , {field: 'type', title: '工单类型', width: 250}
                , {field: 'des', title: '故障描述', width: 250}
                , {field: 'position', title: '故障位置', width: 250}
                , {field: 'timeSubscribe', title: '预约上门时间', width: 250}
                , {
                field: 'progress', title: '状态', width: 250, templet: function (d) {
                    if (d.progress === 4) {
                        return '审核不通过';
                    } else if (d.progress === 3) {
                        return '用户取消';
                    } else if (d.progress === 0) {
                        return '待审核';
                    } else if (d.progress === 1) {
                        return '待处理';
                    } else if (d.progress === 2) {
                        return '已处理';
                    } else {
                        return '出现异常';
                    }
                }
            }
                , {field: 'solver', title: '技术人员', width: 250}
                , {field: 'timeStart', title: '发起时间', width: 250}
                , {field: 'timeDistribution', title: '分配时间', width: 250}
                , {field: 'timeEnd', title: '解决时间', width: 250}
                , {field: 'feedback', title: '用户反馈', width: 250}
                , {title: '操作', align: 'center', width: 100, toolbar: '#order'}
            ]
        ]
        , id: 'tableFour'
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

    $('#searchOrder').on('click', function () {
        table.reload('tableFour', {
            method: 'get'
            , url: '/v2/order/selectOrder'
            , headers: {
                "Cookie": document.cookie
            }
            , where: {
                'orderId': $('#orderId').val(),
                'username': $('#username').val(),
                'sender': $('#sender').val(),
                'tel': $('#tel').val(),
                'type': $('#type').val(),
                'des': $('#des').val(),
                'position': $('#position').val(),
                'timeSubscribe': $('#timeSubscribe').val(),
                'progress': $('#progress').val(),
                'solver': $('#solver').val(),
                'timeStart': $('#timeStart').val(),
                'timeDistribution': $('#timeDistribution').val(),
                'timeEnd': $('#timeEnd').val(),
                'feedback': $('#feedback').val()
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

// 增加工单
function addOrder() {
    var uname = $("#uname").val();
    var passwd = $("#passwd").val();
    var name = $("#name").val();
    if (uname === "" || passwd === "") {
        alert("用户名或密码不能为空");
        return;
    } else if (name === "") {
        alert("真实姓名不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/user/addUser',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "username": uname,
                "password": passwd,
                "name": name,
                "roleType": $('#roleType input[name="status"]:checked ').val()
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

// 修改工单
function updateOrder() {
    var userId = $("#userId").val();
    var uname = $("#uname").val();
    var passwd = $("#passwd").val();
    var name = $("#name").val();
    if (uname === "" || passwd === "") {
        alert("用户名或密码不能为空");
        return;
    } else if (name === "") {
        alert("真实姓名不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/order/updateOrder',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "userId": userId,
                "username": uname,
                "password": passwd,
                "name": name,
                "roleType": $('#roleType input[name="status"]:checked ').val()
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

// 审核工单跳转
function toCheckOrder() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(order)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("orderId", tr.id);
            window.localStorage.setItem("progress", tr.progress);
            window.location.href = "http://localhost:8090/updateOrder.html";
        })
    });
}

// 工单列表跳转
function backToOrderList() {
    window.location.href = "http://localhost:8090/orderList.html";
}

// 审核工单
function checkOrder() {
    $.ajax({
        url: '/v2/order/checkOrder',
        type: 'post',
        headers: {
            "Cookie": document.cookie
        },
        data: {
            "orderId": window.localStorage.getItem("orderId"),
            "progress": $('#progress').val()
        },
        success: function (res) {
            if (res.userMsg !== "") {
                alert(res.userMsg);
            } else {
                alert("发生未知错误，请重试");
            }
            window.location.href = "http://localhost:8090/orderList.html";
        },
        error: function () {
            alert("发生未知错误，请重试");
        }
    });
}

// 删除工单
function delOrder() {
    if ($("#orderId").val() === "") {
        alert("orderId不能为空");
        return;
    }
    $.ajax({
        url: '/v2/order/deleteOrder',
        type: 'post',
        headers: {
            "Cookie": document.cookie
        },
        data: {
            "orderId": $("#orderId").val()
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