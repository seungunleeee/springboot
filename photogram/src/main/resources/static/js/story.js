/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */
 
 //0 현재 로그인한 사용자 아이디
 let principalid=$("#principalid").val();


// (1) 스토리 로드하기
let pagecount;
let pagelimit;
function storyLoad() {
	
	$.ajax({
		url:`/api/image`,
		dataType:"json"
	})
	.done(res=>{
		console.log(res);
		pagecount=res.data.number;
		pagelimit=res.data.totalPages;
		res.data.content.forEach((image)=>{
			let storyitem=getStoryItem(image);
			$("#storyList").append(storyitem);
			
		})
	})	
	.fail(error=>{
		console.log("오류"+ error);
	});

}
storyLoad();

function storyLoadmore(){
	if(pagelimit>pagecount){
	$.ajax({
		url:`/api/image?page=${++pagecount}`,
		dataType:"json"
	})
	.done(res=>{
		console.log(res);
		pagecount=res.data.number;
		pagelimit=res.data.totalPages;
		res.data.content.forEach((image)=>{
			let storyitem=getStoryItem(image);
			$("#storyList").append(storyitem);
			console.log("데이터왔어요");
		})
	})	
	.fail(error=>{
		console.log("오류"+ error);
	});
	}



}

function getStoryItem(image) {

let item=`<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrlString}"
				onerror="this.src='/upload/${image.postimageUri}'" />
		</div>
		<div>${image.user.username}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postimageUri}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			
				`;
				
	if (image.likeState) {
		item += `<button>
									<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>
							</button>`;
	}
	else {
		item += `<button>
							<i class="far fa-heart " id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>
							</button>`;

	}
	

	item += ` </div>	<span class="like"><b id="storyLikeCount-${image.id}">${image.likes.length} </b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>

		<div id="storyCommentList-${image.id}">`;

	image.comments.forEach((comment) => {
		item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
				<p>
					<b>${comment.user.username} :</b> ${comment.content}
				</p>`
		if (comment.user.id == $("#principalid").val()) {
			item += `<button onclick="deleteComment(${comment.id})">
					<i class="fas fa-times"></i>
				</button>
				`}

		item += `</div>`
	});

	item += `</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`

	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
/*console.log("윈도우 scrollTop",$(window).scrollTop());
console.log("문서의 높이",$(document).height());
console.log("윈도우 높이",$(window).height());*/
let checkNum= $(window).scrollTop()-($(document).height()-$(window).height());


if(checkNum<2&& checkNum>-1){
	storyLoadmore();
}

});


// (3) 좋아요, 안좋아요
function toggleLike(id) {
	let likeIcon = $(`#storyLikeIcon-${id}`);
	let likeCountstr=$(`#storyLikeCount-${id}`).text();
	let likeCount=Number(likeCountstr);
	if (likeIcon.hasClass("far")) {
		likeIcon.addClass("fas");
		likeIcon.addClass("active");
		likeIcon.removeClass("far");
		$.ajax({
			type:"post",
			url:`/api/image/${id}/likes`,
			dataType:"json"
		})
		.done(res=>{
			console.log(res);
			likeCount+=1;
			$(`#storyLikeCount-${id}`).text(likeCount);
		})
		.fail(error=>{
			console.log(error);
		})
		
		
	} else {
		likeIcon.removeClass("fas");
		likeIcon.removeClass("active");
		likeIcon.addClass("far");
		
			$.ajax({
				type:"delete",
			url:`/api/image/${id}/likes`,
			dataType:"json"
				
			})
		.done(res=>{
			console.log(res);
			likeCount-=1;
					$(`#storyLikeCount-${id}`).text(likeCount);
		})
		.fail(error=>{
			console.log(error);
		})
	}
}

// (4) 댓글쓰기
function addComment(imageid) {

	let commentInput = $(`#storyCommentInput-${imageid}`);
	let commentList = $(`#storyCommentList-${imageid}`);
	let receivedcontent="";
	let receivedDataId;
	
	let data = {
		imageid:imageid,
		content: commentInput.val()
	}
	
	console.log(data);
	console.log(JSON.stringify(data));
	
	


/*	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}*/
	
	
	$.ajax({
		type:"post",
		url:`api/comment`,
		data:JSON.stringify(data),
		contentType:"application/json; charset=utf-8",
		datatype:"json"
		
		
		
	}).done(res=>{
		console.log("성공");
		console.log(res);
		receivedcontent=res.data.content;
		receivedDataId=res.data.id;
		
			let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${receivedDataId}"> 
			    <p>
			      <b>${res.data.user.username}:</b>
			     ${receivedcontent}
			    </p>
			    <button onclick="deleteComment(${receivedDataId})"><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
		
	}).fail(error=>{
			console.log(error);
			console.log("실패"+error);
			alert(error.responseJSON.data.content);
	})
	


	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment(commentid) {
	$.ajax({
		method:"delete",
		url:`api/comment/${commentid}`,
		dataType:"json"
		
	})
	.done(res=>{
		console.log(res);
		$(`#storyCommentItem-${commentid}`).remove();
	
	})
	.fail(error=>{
		console.log("오류"+err);
		
	})
}







