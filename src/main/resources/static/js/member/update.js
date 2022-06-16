function update(){
    $.ajax({
        url : "/member/update",
        method : "PUT",
        data : {"mname" : $("#mname").val()},
        success : function(result){
            if(result){
                alert("성공");
            }
            else{
                alert("실패");
            }
        }
    })
}
