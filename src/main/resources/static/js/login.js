$("#button").click(
    function (){
        $.ajax({
            url: '/doLogin',
            type: 'post',
            data: {
                "uname": $("#name").val(),
                "passwd": $("#pwd").val()
            },
            success: function (res)
            {
                if (res.code === "00000") {
                    alert("登录成功");
                    location.assign("index.html");
                } else if (res.code === "A0202") {
                    alert("密码错误，请重试");
                } else if (res.code === "A0201") {
                    alert("用户名不存在，请重试");
                } else {
                    alert("发生未知错误，请重试");
                }
            }
        })
    })