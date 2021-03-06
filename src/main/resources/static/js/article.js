document.write('<script src="js/sweetalert.min.js" type="text/javascript" charset="utf-8"></script>');
// 文章数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#articleData'
        , height: 600
        , url: '/v2/article/selectAllArticle'
        , even: true
        , method: 'get'
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'createTime', title: '创建时间', width: 250}
                , {field: 'updateTime', title: '修改时间', width: 250}
                , {field: 'contentPath', title: '内容路径', width: 300}
                , {field: 'author', title: '作者', width: 250}
                , {field: 'title', title: '标题', width: 250}
                , {field: 'des', title: '摘要', width: 250}
                , {field: 'coverPath', title: '封面路径', width: 300}
                , {
                field: 'displayStatus', title: '状态', width: 250, templet: function (d) {
                    return d.displayStatus === 1 ? '展示' : '隐藏';
                }
            }
                , {title: '操作', align: 'center', width: 180, toolbar: '#article'}
            ]
        ]
        , id: 'tableFive'
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

    $('#searchArticle').on('click', function () {
        table.reload('tableFive', {
            method: 'get'
            , url: '/v2/article/selectArticle'
            , where: {
                'articleId': $('#articleId').val(),
                'author': $('#author').val(),
                'displayStatus': $('#displayStatus').val(),
                'title': $('#title').val(),
                'des': $('#des').val()
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

// 增加文章
function addArticle() {
    var contentPath = $("#contentPath").val();
    var author = $("#author").val();
    if (contentPath === "" || contentPath === "") {
        swal({
            icon: 'error',
            title: '添加失败',
            text: '文章网址不能为空',
        })
        // alert("文章网址不能为空");
        return;
    } else if (author === "") {
        swal({
            icon: 'error',
            title: '添加失败',
            text: '文章作者不能为空',
        })
        //alert("文章作者不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/article/addArticle',
            type: 'post',
            data: {
                "contentPath": contentPath,
                "author": author,
                "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
            },
            success: function (res) {
                if (res.userMsg !== "") {
                    swal({
                        icon: 'success',
                        title: '添加成功',
                        text: res.userMsg,
                    })
                    //alert(res.userMsg);
                } else {
                    swal({
                        icon: 'error',
                        title: '添加失败',
                        text: '发生未知错误，请重试',
                    })
                    // alert("发生未知错误，请重试");
                }
            },
            error: function () {
                swal({
                    icon: 'error',
                    title: '添加失败',
                    text: '发生未知错误，请重试',
                })
                // alert("发生未知错误，请重试");
            }
        });
    }
}

// 删除文章
function delArticle() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(article)', function (obj) {
                var tr = obj.data;
                var msg = "您真的确定要删除吗？";
                if (confirm(msg) === true) {
                    $.ajax({
                        url: '/v2/article/deleteArticle',
                        type: 'post',
                        data: {
                            "articleId": tr.id
                        },
                        success: function (res) {
                            if (res.userMsg !== "") {
                                swal({
                                    icon: 'success',
                                    title: '删除成功',
                                    text: res.userMsg,
                                })
                                //alert(res.userMsg);
                                location.reload();
                            } else {
                                swal({
                                    icon: 'error',
                                    title: '删除失败',
                                    text: '发生未知错误，请重试',
                                })
                                // alert("发生未知错误，请重试");
                            }
                        },
                        error: function () {
                            swal({
                                icon: 'error',
                                title: '删除失败',
                                text: '发生未知错误，请重试',
                            })
                            //alert("发生未知错误，请重试");
                        }
                    });
                } else {
                    return false;
                }
            })
        });
    })
}

// 修改文章
function updateArticle() {
    var articleId = $("#articleId").val();
    var contentPath = $("#contentPath").val();
    var author = $("#author").val();
    var title = $("#title").val();
    var des = $("#des").val();
    var coverPath = $("#coverPath").val();
    if (articleId === "") {
        swal({
            icon: 'error',
            title: '修改失败',
            text: '文章Id不能为空',
        })
        // alert("文章Id不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/article/updateArticle',
            type: 'post',
            data: {
                "articleId": articleId,
                "contentPath": contentPath,
                "author": author,
                "displayStatus": $('#displayStatus input[name="status"]:checked ').val(),
                "title": title,
                "des": des,
                "coverPath": coverPath
            },
            success: function (res) {
                if (res.userMsg !== "") {
                    swal({
                        icon: 'success',
                        title: '修改成功',
                        text: res.userMsg,
                    })
                    //alert(res.userMsg);
                } else {
                    swal({
                        icon: 'error',
                        title: '修改失败',
                        text: '发生未知错误，请重试',
                    })
                    //alert("发生未知错误，请重试");
                }
            },
            error: function () {
                swal({
                    icon: 'error',
                    title: '修改失败',
                    text: '发生未知错误，请重试',
                })
                //alert("发生未知错误，请重试");
            }
        });
    }
}

// 跳转到修改文章界面
function toUpdateArticle() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(article)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("articleId", tr.id);
            window.localStorage.setItem("contentPath", tr.contentPath);
            window.localStorage.setItem("author", tr.author);
            window.localStorage.setItem("title", tr.title);
            window.localStorage.setItem("des", tr.des);
            window.localStorage.setItem("coverPath", tr.coverPath);
            window.location.href = "/updateArticle.html";
        })
    });
}

// 返回文章列表
function backToArticleList() {
    window.location.href = "/articleList.html";
}