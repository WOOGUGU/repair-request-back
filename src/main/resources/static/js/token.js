//将token放入请求头中
//key为wrnm,value为token
function AJAX(url,type,data,success)
{
    $.ajax({
        url:url,
        type:type,
        headers: {
            'wrnm':localStorage.wrnm
        },
        data:data,
        success:function (res)
        {
            if(res.code!=200)
            {
                alert("你无权访问该资源")
            }
            success(res);
        },
        error:function (res)
        {
                alert("出现异常")
        }
    });
}

//页面顶部检验令牌有效性
function TOKEN_VERIFY()
{
    var token=localStorage.wrnm;
    if(token===undefined)
    {
        location.assign("login.html");
        return;
    }
    $.ajax({
        url:'verify',
        type:'post',
        headers: {
            'wrnm':token
        },
        success:function (res)
        {
            if(res.data===false)
            {
                location.assign("login.html");
            }
        },
        error:function (res)
        {
            location.assign("login.html");
        }
    })
}