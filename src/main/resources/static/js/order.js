document.write('<script src="js/sweetalert.min.js" type="text/javascript" charset="utf-8"></script>');
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
        , even: true
        , method: 'get'
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
                , {field: 'feedback', title: '维修反馈', width: 250}
                , {field: 'stars', title: '满意星级', width: 150}
                , {field: 'remark', title: '备注', width: 150}
                , {title: '操作', align: 'center', width: 180, toolbar: '#order'}
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
                'feedback': $('#feedback').val(),
                'stars': $('#stars').val()
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
        Swal.fire({
            icon: 'error',
            title: '增加失败',
            text: '用户名或密码不能为空',
        })
        //alert("用户名或密码不能为空");
        return;
    } else if (name === "") {
        Swal.fire({
            icon: 'error',
            title: '增加失败',
            text: '真实姓名不能为空',
        })
        // alert("真实姓名不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/user/addUser',
            type: 'post',
            data: {
                "username": uname,
                "password": passwd,
                "name": name,
                "roleType": $('#roleType input[name="status"]:checked ').val()
            },
            success: function (res) {
                if (res.userMsg !== "") {
                    Swal.fire({
                        icon: 'success',
                        title: '增加成功',
                        text: res.userMsg,
                    })
                    // alert(res.userMsg);
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '增加失败',
                        text: '发生未知错误，请重试',
                    })
                    //alert("发生未知错误，请重试");
                }
            },
            error: function () {
                Swal.fire({
                    icon: 'error',
                    title: '增加失败',
                    text: '用户名或密码不能为空',
                })
                // alert("发生未知错误，请重试");
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
        Swal.fire({
            icon: 'error',
            title: '修改失败',
            text: '用户名或密码不能为空',
        })
        //alert("用户名或密码不能为空");
        return;
    } else if (name === "") {
        Swal.fire({
            icon: 'error',
            title: '修改失败',
            text: '真实姓名不能为空',
        })
        //alert("真实姓名不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/order/updateOrder',
            type: 'post',
            data: {
                "userId": userId,
                "username": uname,
                "password": passwd,
                "name": name,
                "roleType": $('#roleType input[name="status"]:checked ').val()
            },
            success: function (res) {
                if (res.userMsg !== "") {
                    Swal.fire({
                        icon: 'success',
                        title: '修改成功',
                        text: res.userMsg,
                    })
                    // alert(res.userMsg);
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '修改失败',
                        text: '发生未知错误，请重试',
                    })
                    //   alert("发生未知错误，请重试");
                }
            },
            error: function () {
                Swal.fire({
                    icon: 'error',
                    title: '修改失败',
                    text: '发生未知错误，请重试',
                })
                //  alert("发生未知错误，请重试");
            }
        });
    }
}

// 分配维修员页面跳转
function toSendRepairman() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(order)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("orderId", tr.id);
            window.localStorage.setItem("progress", tr.progress);
            window.location.href = "/sendRepairman.html";
        })
    });
}

// 工单列表跳转
function backToOrderList() {
    window.location.href = "/orderList.html";
}

// 分配维修员
function sendRepairman() {
    $.ajax({
        url: '/v2/order/sendRepairman',
        type: 'post',
        data: {
            "orderId": window.localStorage.getItem("orderId"),
            "solver": $('#solver').val()
        },
        success: function (res) {
            if (res.userMsg !== "") {
                Swal.fire({
                    icon: 'success',
                    title: '分配成功',
                    text: res.userMsg,
                })
                //alert(res.userMsg);
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '分配失败',
                    text: '发生未知错误，请重试',
                })
                //  alert("发生未知错误，请重试");
            }
            window.location.href = "/orderList.html";
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: '修改失败',
                text: '发生未知错误，请重试',
            })
            //alert("发生未知错误，请重试");
        }
    });
}

// 审核工单不通过
function rejectOrder() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(order)', function (obj) {
                var tr = obj.data;
                var info = prompt("请输入审核不通过的原因：");
                if (info == null) {
                    return;
                } else if (info == '') {
                    Swal.fire({
                        icon: 'error',
                        title: '审核失败',
                        text: '请输入原因后重试',
                    })
                    //  alert("请输入原因后重试");
                    return;
                }
                $.ajax({
                    url: '/v2/order/checkOrder',
                    type: 'post',
                    data: {
                        "orderId": tr.id,
                        "progress": 4,
                        "remark": info
                    },
                    success: function (res) {
                        if (res.userMsg !== "") {
                            Swal.fire({
                                icon: 'success',
                                title: '审核成功',
                                text: res.userMsg,
                            })
                            // alert(res.userMsg);
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: '审核失败',
                                text: '发生未知错误，请重试',
                            })
                            // alert("发生未知错误，请重试");
                        }
                        window.location.href = "/orderList.html";
                    },
                    error: function () {
                        Swal.fire({
                            icon: 'error',
                            title: '审核失败',
                            text: '发生未知错误，请重试',
                        })
                        //   alert("发生未知错误，请重试");
                    }
                });
            })
        });
    })
}

// 删除工单
function delOrder() {
    if ($("#orderId").val() === "") {
        Swal.fire({
            icon: 'error',
            title: '删除失败',
            text: 'orderId不能为空',
        })
        // alert("orderId不能为空");
        return;
    }
    $.ajax({
        url: '/v2/order/deleteOrder',
        type: 'post',
        data: {
            "orderId": $("#orderId").val()
        },
        success: function (res) {
            if (res.userMsg !== "") {
                Swal.fire({
                    icon: 'success',
                    title: '删除成功',
                    text: res.userMsg,
                })
                //  alert(res.userMsg);
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '删除失败',
                    text: '发生未知错误，请重试',
                })
                //  alert("发生未知错误，请重试");
            }
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: '删除失败',
                text: '发生未知错误，请重试',
            })
            //  alert("发生未知错误，请重试");
        }
    });
}