/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(touserid,obj) {
	if ($(obj).text() === "구독취소") {
		
		
		
		$.ajax({
			type:"delete",
			url:"/api/subscribe/"+touserid,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
			
		}).fail(error=>{
			console.log("구독취소실패",error);
		});
		
		
	} else {
				
		$.ajax({
			type:"post",
			url:"/api/subscribe/"+touserid,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
			
		}).fail(error=>{
			console.log("구독하기실패",error);
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserid) {
	alert(pageUserid);
	$(".modal-subscribe").css("display", "flex");
	
	$.ajax({
		url : `/api/user/${pageUserid}/subscribe`,
		dataType:"Json"
		
	}).done(res=>{
		console.log(res.data);
		
		
		res.data.forEach((user)=>{
			let item=getSubscribeModalItem(user);
			$("#subscribeModalList").append(item);
			
		});
	}).fail(error=>{
		console.log("구독정보오류"+error);
	});
	
}

function getSubscribeModalItem(user) {
	let item = `<div class="subscribe-list" id="subscribeModalList-${user.id}">
	<div class="subscribe__item" id="subscribeModalItem-1">
		<div class="subscribe__img">
			<img src="/upload/${user.profileImageUrlString}" onerror="this.src='/images/person.jpeg'"/>
		</div>
		<div class="subscribe__text">
			<h2>${user.username}</h2>
		</div>
		<div class="subscribe__btn">`
		
		
		if(!user.equaluserState){
			if(user.subscribeState){
			item+=`<button class="cta blue" onclick="toggleSubscribe(${user.id},this)">구독취소</button>`
			}
			else{
				item+=`<button class="cta " onclick="toggleSubscribe(${user.id},this)">구독하기</button>`
			}
		}
		
			
			
			
	item+=`</div>
		</div>`
	
	
	return item;
}




// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserid ,principalid) {
	console.log("pageUserid",pageUserid);
		console.log("principalid",principalid);
		
	if(pageUserid!=principalid){
		alert("프로필 사진을 수정할 수 없는 유저입니다.")
		return ;
	}
	//FormData객체를 이용해 Form태그의 필드와 그 값을 나타내는 일련의 ,key/value 쌍을 담을 수 ㅣㅆ다.
	let profileimageForm = $("#userProfileImageForm")[0];
	console.log(profileimageForm);
	
	
	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		
		let formData= new FormData(profileimageForm);
	
	$.ajax({
		type:"put",
		url:`/api/user/${principalid}/profileimageUrl`,
		data:formData,
		contentType:false, //필수 x-www-form-urlencloded 파싱 방지
		processData:false, //필수 contentType false로 줬을 때 Query String 자동설정됨, 해제
		enctype:"multipart/form-data",
		dataType:"json"
	}).done(res=>{
		
		
		let reader = new FileReader();
		reader.onload = (e) => {
			$("#userProfileImage").attr("src", e.target.result);
		}
		reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		
		
	}).fail(error=>{
		
		console.log("사진 전송실패",error)
	});
		
		
		
		
		
		
		

		// 사진 전송 성공시 이미지 변경
		
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






