$("#mpassword").keyup(function(e){
   if(e.keyCode == 13){
        login();
    }
});

function login(){
    let mid = $("#mid").val();
    let mpassword = $("#mpassword").val();
    $.ajax({
        url : "/member/logincontroller",
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