<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="HOME MAIN" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5 text-xl">
	<div class="container mx-auto px-3">
		<div>제작자 : ToToMaRo</div>
	</div>
</section>

<div class="popup text-2xl inline">Popup Test</div>
<div class="layer-bg"></div>
<div class="layer">
	<div class="flex justify-between">
		<div class="text-2xl">POPUP</div>
		<div class="close-btn">
			<div></div>
			<div></div>
		</div>
	</div>
	<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Commodi repudiandae expedita vero nihil architecto
		nostrum blanditiis unde beatae! Id iusto ducimus minima. Libero quam voluptas velit eius consequatur delectus
		quisquam.</div>
</div>

<%@ include file="../common/foot.jspf"%>