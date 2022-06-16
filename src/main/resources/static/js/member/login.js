function login(){
    let mid = $("#mid").val();
    let mpassword = $("#mpassword").val();
    $.ajax({
        url : "/member/login",
        method : "POST",
        data : {"mid" : mid , "mpassword" : mpassword},
        success : function(result){
            if(result){
                location.href = "/"
            }
            else{
                alert("로그인 실패");
            }

        }
    })
}