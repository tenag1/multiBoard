//  replaceAll prototype 선언
// 기존객체.prototype.함수 = 함수 : 객체에 메서드 추가!!!!
String.prototype.replaceAll = function(org, dest) {
	// 문자열.split(문자열) : 구분자로 구분하여 배열을 만든다. --> 원본글자 모두 사라진다.
	// 문자열.join(문자열) : 문자열을 구분자로 하여 배열을 합쳐준다. --> 원본글자 자리에 대상글자 들어간다.
	return this.split(org).join(dest);
}
// null이나 공백만 입력되었는지 검사하는 함수
function isNullCheck(str){
	return str==null || str.trim().length==0;
}
// 모든 html태그를 제거하여 리턴하는 함수
function removeHTMLAll(str){
	return str.replace(/(<([^>]+)>)/ig, "");
}
// 특정 html태그를 제거하여 리턴하는 함수
function removeHTMLTag(str, tag){
	str = str.replaceAll("<"+tag+">","");
	str = str.replaceAll("</"+tag+">","");
	return str;
}
/*
 * path : 전송 URL params : 전송 데이터 {'q':'a','s':'b','c':'d'...}으로 묶어서 배열 입력 method :
 * 전송 방식(생략가능)
 */
function post_to_url(path, params, method) {
	method = method || "POST"; // 메서드 지정
	var form = document.createElement("form"); // 폼 생성
	form.setAttribute("method", method); // 메서드 속성 추가
	form.setAttribute("action", path); // 액션 지정
	for ( var key in params) { // 파라메터를 input 태그로 만들어 폼에 추가
		var hiddenField = document.createElement("input"); // input태그 생성
		hiddenField.setAttribute("type", "hidden"); // 타입 속성을 hidden으로 지엉
		hiddenField.setAttribute("name", key); // name속성 지정
		hiddenField.setAttribute("value", params[key]); // value속성 지정
		form.appendChild(hiddenField); // 폼에 추가
	}
	document.body.appendChild(form); // body에 추가
	form.submit(); // 폼 전송
}

// 실제로 구동시킬 때 입력 예제
// post_to_url('http://example.com/', {'q':'a'});

