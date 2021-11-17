{
    // 添加用户
    function submitAddUser() {
        let username = $("#username").val();
        let password = $("#password").val();
        let name = $("#name").val();

        $.ajax({
            type: "post",
            url: "/addUser",
            data: {
                'username': username,
                'password': password,
                'name': name
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("处理成功！");
                }
            }
        })
    }
}