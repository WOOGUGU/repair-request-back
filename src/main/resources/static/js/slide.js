document.write('<script src="js/sweetalert.min.js" type="text/javascript" charset="utf-8"></script>');
// 轮播图数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#slideData'
        , height: 600
        , url: '/v2/slide/selectAllSlide'
        , even: true
        , method: 'get'
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'imgPath', title: '图片路径', width: 500}
                , {field: 'author', title: '上传者', width: 250}
                , {field: 'submitTime', title: '上传时间', width: 250}
                , {title: '操作', align: 'center', width: 100, toolbar: '#slide'}
            ]
        ]
        , id: 'tableSix'
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

    $('#searchSlide').on('click', function () {
        table.reload('tableSix', {
            method: 'get'
            , url: '/v2/slide/selectSlide'
            , where: {
                'slideId': $('#slideId').val(),
                'author': $('#author').val(),
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

// 删除轮播图
function delSlide() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(slide)', function (obj) {
                var tr = obj.data;
                var msg = "您真的确定要删除吗？";
                if (confirm(msg) === true) {
                    $.ajax({
                        url: '/v2/slide/deleteSlide',
                        type: 'post',
                        data: {
                            "slideId": tr.id
                        },
                        success: function (res) {
                            if (res.userMsg !== "") {
                                Swal.fire({
                                    icon: 'success',
                                    title: '删除成功',
                                    text: res.userMsg,
                                })
                                //alert(res.userMsg);
                                location.reload();
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: '删除失败',
                                    text: '发生未知错误，请重试',
                                })
                                //alert("发生未知错误，请重试");
                            }
                        },
                        error: function () {
                            Swal.fire({
                                icon: 'error',
                                title: '删除失败',
                                text: '发生未知错误，请重试',
                            })
                            // alert("发生未知错误，请重试");
                        }
                    });
                } else {
                    return false;
                }
            })
        });
    })
}

// 跳转到修改轮播图页面
function toUpdateSlide() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(slide)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("slideId", tr.id);
            window.localStorage.setItem("slideAuthor", tr.author);
            window.location.href = "/updateSlide.html";
        })
    });
}

function backToSlideList() {
    window.location.href = "/slideList.html";
}