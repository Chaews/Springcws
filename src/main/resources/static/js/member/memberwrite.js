function memberwrite(){
    let form = $("#signupform")[0];
    let formdata = new FormData(form);
    $.ajax({
        url : "/member/signup",
        method : "POST",
        data : formdata,
        contentType : false,
        processData : false,
        success : function(result){
            if(result==true){
                location.href ="/member/login"
            }
            else{
                alert("회원가입 실패! [서비스오류 : 관리자에게 문의]")
            }
        }
    })
}