// (1) 회원정보 수정
function update( userid,event) {
	
	event.preventDefault(); //폼태크 액션을 막아줌
	
alert("update");

let data=$("#profileUpdate").serialize();
console.log(data);

$.ajax({
	type:"put",
	url:`/api/user/${userid}`,
	data:data,
	contentType:"application/x-www-form-urlencoded; charset=utf-8",
	datatype:"json" //http 상태코드 200 ->성공, 400->실패
}).done(res=>{
	console.log("update 성공",res);
	location.href=`/user/${userid}`;

}).fail(error=>{
	console.log("update 실패",error.responseJSON.data);
	var report;
	var a="";
	var b=error.responseJSON.data;
	console.log(error.responseJSON)
	if(error.responseJSON.message)a=error.responseJSON.message;
	if(error.responseJSON.data){
	for (const [key, value] of Object.entries(b)) {
		console.log(`${key}  ${value}`);
		if(key!="undefined") a+=`${key}  ${value}`;
		}
 
}
	
	alert(a);
})
}


/*	location.href=`/user/${userid}`*/