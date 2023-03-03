<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>FH Admin</title>
    <!-- HTML5 Shim and Respond.js IE10 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 10]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
    <!-- Meta -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="author" content="FH Admin QQ313596790" />

    <link rel="icon" href="../../../assets/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="../../../assets/fonts/material/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="../../../assets/fonts/fontawesome/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="../../../assets/plugins/animation/css/animate.min.css">
    <link rel="stylesheet" href="../../../assets/css/style.css">

    <!-- vue -->
	<script src="../../../assets/js-vue/vue.js"></script>
    <!--全局配置-->
    <script src="../../../assets/js-v/config.js"></script>

</head>

<body>
    
    <!-- [加载状态 ] start -->
    <div class="loader-bg">
        <div class="loader-track">
            <div class="loader-fill"></div>
        </div>
    </div>
    <!-- [ 加载状态  ] End -->

    <!-- [ 主内容区 ] start -->
        <div class="pcoded-wrapper" id="app">
            <div class="pcoded-content">
                <div class="pcoded-inner-content">
                    <div class="main-body">
                        <div class="page-wrapper">
                            <!-- [ Main Content ] start -->
                            <div class="row">

							    <!-- [ Hover-table ] start -->
                                <div class="col-xl-12">
                                    <div class="card">
         
										<!-- 检索  -->
										<div style="padding-left: 20px;padding-top: 15px;">
										<table style="margin-top:5px;">
											<tr>
												<td>
							                        <div class="input-group input-group-sm mb-3">
	                                                	<input class="form-control" type="text" v-model="KEYWORDS" placeholder="这里输入关键词" />
	                                            	</div>
												</td>
												<td style="vertical-align:top;padding-left:5px;">
													<a class="btn btn-light btn-sm" v-on:click="getList(${objectNameUpper}_ID)" style="width: 23px;height:30px;margin-top:1px;" title="检索"><i style="margin-top:-3px;margin-left: -6px;"  class="feather icon-search"></i></a>
													<a v-show="toExcel" class="btn btn-light btn-sm" v-on:click="goExcel" style="width: 23px;height:30px;margin-top:1px;margin-left: -9px;" title="导出到excel表格">
														<i style="margin-top:-3px;margin-left: -6px;" class="mdi mdi-cloud-download"></i>
													</a>
												</td>
												<td v-show="loading">
												<!-- [加载状态 ] start -->
										    	<div class="d-flex justify-content-center" style="margin-top:-10px;">
		                                            <div class="spinner-grow spinner-grow-sm" role="status">
		                                                <span class="sr-only">Loading...</span>
		                                            </div>
		                                        </div>
										    	<!-- [ 加载状态  ] End -->
												</td>
											</tr>
										</table>
										</div>
										<!-- 检索  -->
										<div class="card-block table-border-style" style="margin-top: -15px">
                                			<div class="table-responsive">
                                    			<table class="table table-hover">
													<thead>
														<tr>
															<th style="width:50px;">NO</th>
															<th>名称</th>
														<#list fieldList as var>
															<th>${var[2]}</th>
														</#list>
															<th>操作</th>
														</tr>
													</thead>
																			
													<tbody>
														<!-- 开始循环 -->	
														<template v-for="(data,index) in varList">
																<tr>
																	<td>{{page.showCount*(page.currentPage-1)+index+1}}</td>
																	<td><a v-on:click="getList(data.${objectNameUpper}_ID)" style="cursor:pointer;">{{data.NAME}} <i class="feather icon-chevron-down"></i></a></td>
															<#list fieldList as var>
																<#if var[1] == 'String' && var[7] != 'null'>
																	<td>{{data.DNAME${var_index+1}}}</td>
																<#else>
																	<td>{{data.${var[0]}}}</td>
																</#if>
															</#list>
																	<td>
																		<a v-show="cha" title="查看" v-on:click="goView(data.${objectNameUpper}_ID)" style="cursor:pointer;"><i class="feather icon-eye"></i></a>
																		<a v-show="edit" title="修改" v-on:click="goEdit(data.${objectNameUpper}_ID)" style="cursor:pointer;"><i class="feather icon-edit-2"></i></a>
									                 					<a v-show="del" title="删除" v-on:click="goDel(data.${objectNameUpper}_ID)" style="cursor:pointer;"><i class="feather icon-x"></i></a>
																	</td>
																</tr>
														</template>
														<tr v-show="varList.length==0">
															<td colspan="10">没有相关数据</td>
														</tr>
													</tbody>
												</table>
												
												<table style="width:100%;margin-top:15px;">
													<tr>
														<td style="vertical-align:top;">
															<a v-show="add" class="btn btn-light btn-sm" v-on:click="goAdd(${objectNameUpper}_ID)">新增</a>
															<template v-if="'0' != ${objectNameUpper}_ID">
															<a class="btn btn-light btn-sm" v-on:click="getList(PARENT_ID)">返回</a>
															</template>
														</td>
														<td style="vertical-align:top;"><div style="float: right;padding-top: 0px;margin-top: 0px;" v-html="page.pageStr"></div></td>
													</tr>
												</table>
											</div>
                                		</div>	
			
                                    </div>
                                </div>
                                <!-- [ Hover-table ] end -->

                            </div>
                            <!-- [ Main Content ] end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <!-- [ 主内容区 ] end -->
    
<script type="text/javascript" src="../../../assets/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../../../assets/js/pre-loader.js"></script>
<script src="../../../assets/plugins/sweetalert/js/sweetalert.min.js"></script>
<!-- 表单验证提示 -->
<script src="../../../assets/js/jquery.tips.js"></script>

<script type="text/javascript">

var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],				//list
		page: [],					//分页类
		KEYWORDS: '',				//检索条件 关键词
		${objectNameUpper}_ID: '0',	//主键ID
		PARENT_ID: '0',				//上级ID
		showCount: -1,				//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,				//当前页码
		add:false,					//增
		del:false,					//删
		edit:false,					//改
		cha:false,					//查
		toExcel:false,				//导出到excel权限
		loading:false				//加载状态
    },

	methods: {
		
        //初始执行
        init() {
        	var id = this.getUrlKey('id');  //链接参数, 从树点过来
        	if(null != id){
        		this.${objectNameUpper}_ID = id;
        	}
    		this.getList(this.${objectNameUpper}_ID);
        },

        //获取列表
        getList: function(F_ID){
        	this.${objectNameUpper}_ID = F_ID;
        	this.loading = true;
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'${objectNameLower}/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {${objectNameUpper}_ID:this.${objectNameUpper}_ID,KEYWORDS:this.KEYWORDS,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.page = data.page;
				 	 vm.PARENT_ID = data.PARENT_ID;
        			 vm.hasButton();
        			 vm.loading = false;
        		 }else if ("exception" == data.result){
                 	showException("${TITLE}",data.exception);//显示异常
                 }
        		}
        	}).done().fail(function(){
                swal("登录失效!", "请求服务器无响应，稍后再试", "warning");
                setTimeout(function () {
                	window.location.href = "../../login.html";
                }, 2000);
            });
        },
        
        //新增
		goAdd: function (${objectNameUpper}_ID){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '../../${packageName}/${objectNameLower}/${objectNameLower}_edit.html?PARENT_ID='+${objectNameUpper}_ID;
			 diag.Width = 1000;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				 var varSon = diag.innerFrame.contentWindow.document.getElementById('showform');
    			 if(varSon != null && varSon.style.display == 'none'){
    				 vm.getList(vm.${objectNameUpper}_ID);
    				 parent.vm.getData(); //刷新父页面
    			 }
				 diag.close();
			 };
			 diag.show();
		},
		
		//修改
		goEdit: function (${objectNameUpper}_ID){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '../../${packageName}/${objectNameLower}/${objectNameLower}_edit.html?PARENT_ID='+this.${objectNameUpper}_ID+'&${objectNameUpper}_ID='+${objectNameUpper}_ID;
			 diag.Width = 1000;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				 var varSon = diag.innerFrame.contentWindow.document.getElementById('showform');
    			 if(varSon != null && varSon.style.display == 'none'){
    				 vm.getList(vm.${objectNameUpper}_ID);
    				 parent.vm.getData(); //刷新父页面
    			 }
				 diag.close();
			 };
			 diag.show();
		},

		//查看
		goView: function (${objectNameUpper}_ID){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看";
			 diag.URL = '../../${packageName}/${objectNameLower}/${objectNameLower}_view.html?PARENT_ID='+this.${objectNameUpper}_ID+'&${objectNameUpper}_ID='+${objectNameUpper}_ID;
			 diag.Width = 1000;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				 diag.close();
			 };
			 diag.show();
		},
        
		//删除
		goDel: function (Id){
			swal({
				title: '',
	            text: "确定要删除 吗?",
	            icon: "warning",
	            buttons: true,
	            dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                	this.loading = true;
	            	$.ajax({
	            		xhrFields: {
	                        withCredentials: true
	                    },
	        			type: "POST",
	        			url: httpurl+'${objectNameLower}/delete',
	        	    	data: {${objectNameUpper}_ID:Id,tm:new Date().getTime()},
	        			dataType:'json',
	        			success: function(data){
	        				 if("success" == data.result){
	        					 swal("删除成功", "已经从列表中删除!", "success");
	        					 vm.getList(vm.${objectNameUpper}_ID);
	            				 parent.vm.getData(); //刷新父页面
	        				 }else if("error" == data.result){
	        					swal("删除失败!", "删除失败！请先删除子级或删除占用资源!", "warning");
	        					vm.loading = false;
	        				 }
	        			}
	        		});
                }
            });
		},
        
        //导出excel
		goExcel: function (){
			swal({
               	title: '',
                text: '确定要导出到excel吗?',
                icon: "warning",
                buttons: true,
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                	window.location.href = httpurl+'${objectNameLower}/excel';        	
                }
            });
		},

      	//根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        },
        
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = '${objectNameLower}:add,${objectNameLower}:del,${objectNameLower}:edit,${objectNameLower}:cha,toExcel';
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'head/hasButton',
        		data: {keys:keys,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			vm.add = data.${objectNameLower}fhadminadd;
        			vm.del = data.${objectNameLower}fhadmindel;
        			vm.edit = data.${objectNameLower}fhadminedit;
					vm.cha = data.${objectNameLower}fhadmincha;
        			vm.toExcel = data.toExcel;
        		 }else if ("exception" == data.result){
                 	showException("按钮权限",data.exception);//显示异常
                 }
        		}
        	})
        },

	    //-----分页必用----start
	    nextPage: function (page){
	    	this.currentPage = page;
	    	this.getList(this.${objectNameUpper}_ID);
	    },
	    changeCount: function (value){
	    	this.showCount = value;
	    	this.getList(this.${objectNameUpper}_ID);
	    },
	    toTZ: function (){
	    	var toPaggeVlue = document.getElementById("toGoPage").value;
	    	if(toPaggeVlue == ''){document.getElementById("toGoPage").value=1;return;}
	    	if(isNaN(Number(toPaggeVlue))){document.getElementById("toGoPage").value=1;return;}
	    	this.nextPage(toPaggeVlue);
	    }
	   	//-----分页必用----end
	
	},
	
	mounted(){
	    this.init();
	}
	
})

</script>

</body>
</html>