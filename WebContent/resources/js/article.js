function pageList(page) {
	$("#pageNo").val(page);
	$("#frm").get(0).action = "list.do";
	$("#frm").get(0).submit();
}

$(document).ready(function () {
	$('#closeBtn').click(function () {
		$('#myModal').attr('style', 'display: none;');
		$('#myModal').attr('aria-hidden', 'true');
		$('#myModal').attr('class', 'modal fade');
	});
});