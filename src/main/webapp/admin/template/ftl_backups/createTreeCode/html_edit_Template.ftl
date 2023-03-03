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
    <link rel="stylesheet" href="../../../assets/fonts/fontawesome/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="../../../assets/plugins/animation/css/animate.min.css">
    <link rel="stylesheet" href="../../../assets/css/style.css">
    
    <!-- 日期插件 -->
    <link rel="stylesheet" href="../../../assets/plugins/material-datetimepicker/css/bootstrap-material-datetimepicker.css">
    
    <!-- select插件 -->
    <link rel="stylesheet" href="../../../assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="../../../assets/plugins/multi-select/css/multi-select.css">
    
    <!-- vue -->
	<script src="../../../assets/js-vue/vue.js"></script>
    <!--全局配置-->
    <script src="../../../assets/js-v/config.js"></script>

</head>

<body style="background-color: white">
    
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
					
									 <div style="width: 100%;">
										<div id="showform">
										<h6 style="padding-left: 6px;">上级：{{'0' == PARENT_ID ?'(无) 此项为顶级':pds.NAME}}</h6>
							            <div class="input-group input-group-sm mb-3">
		                                <div class="input-group-prepend">
		                                        <span class="input-group-text" style="width: 79px;"><span style="width: 100%;">名称</span></span>
		                                    </div>
		                                    <input type="text" class="form-control" ref="NAME" id="NAME" v-model="pd.NAME"  placeholder="这里输入名称" title="名称">
		                                </div>
							<#list fieldList as var>
								<#if var[3] == "是">
									<#if var[1] == 'Date'>
							            <div class="input-group input-group-sm mb-3" style="margin-top: -10px;">
		                                    <div class="input-group-prepend">
		                                        <span class="input-group-text" style="width: 79px;"><span style="width: 100%;">${var[2]}</span></span>
		                                    </div>
		                                    <input type="text" class="form-control date" ref="${var[0] }" id="${var[0] }" maxlength="32" placeholder="这里输入${var[2] }" title="${var[2] }">
		                                </div>
									<#elseif var[1] == 'Integer'>
							            <div class="input-group input-group-sm mb-3" style="margin-top: -10px;">
		                                    <div class="input-group-prepend">
		                                        <span class="input-group-text" style="width: 79px;"><span style="width: 100%;">${var[2] }</span></span>
		                                    </div>
		                                    <input type="number" class="form-control" ref="${var[0] }" id="${var[0] }" v-model.number="pd.${var[0]}" oninput="if(value.length>${var[5] })value=value.slice(0,${var[5] })" placeholder="这里输入${var[2] }" title="${var[2] }">
		                                </div>
									<#elseif var[1] == 'Double'>
							            <div class="input-group input-group-sm mb-3" style="margin-top: -10px;">
		                                    <div class="input-group-prepend">
		                                        <span class="input-group-text" style="width: 79px;"><span style="width: 100%;">${var[2] }</span></span>
		                                    </div>
		                                    <input type="number" class="form-control" ref="${var[0] }" id="${var[0] }" v-model="pd.${var[0]}" maxlength="${var[5]}" placeholder="这里输入${var[2] }" title="${var[2] }">
		                                </div>
									<#else>
									<#if var[7] != 'null'>
							            <div class="input-group input-group-sm mb-3" style="margin-top: -10px;">
		                                    <div class="input-group-prepend">
		                                        <span class="input-group-text" style="width: 79px;"><span style="width: 100%;">${var[2] }</span></span>
		                                    </div>
		                                    <select ref="${var[0] }" id="${var[0] }" v-model="${var[0]}" title="${var[2] }" data-placeholder="${var[2] }" style="width:908px;padding-left:10px;border: 1px solid #CED4DA;transparent;appearance:none;-moz-appearance:none;-webkit-appearance:none;"></select>
		                                </div>
									<#else>
							            <div class="input-group input-group-sm mb-3" style="margin-top: -10px;">
		                                    <div class="input-group-prepend">
		                                        <span class="input-group-text" style="width: 79px;"><span style="width: 100%;">${var[2] }</span></span>
		                                    </div>
		                                    <input type="text" class="form-control" ref="${var[0] }" id="${var[0] }" v-model="pd.${var[0]}" maxlength="${var[5] }" placeholder="这里输入${var[2] }" title="${var[2] }">
		                                </div>
									</#if>
									</#if>
								</#if>
							</#list>
										<div class="input-group" style="margin-top:10px;background-color: white" >
							            	<span style="width: 100%;text-align: center;">
							            		<a class="btn btn-light btn-sm" v-on:click="save">保存</a>
							            		<a class="btn btn-light btn-sm" onclick="top.Dialog.close();">取消</a>
							            	</span>
							            </div>
							           </div>
							            <!-- [加载状态 ] start -->
									    <div id="jiazai" style="display:none;margin-top:50px;">
									    	<div class="d-flex justify-content-center">
		                                        <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">
	                                                <span class="sr-only">Loading...</span>
	                                            </div>
	                                        </div>
	                                    </div>
									    <!-- [ 加载状态  ] End -->
									</div>
	    
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

<!-- 日期插件 -->
<script src="../../../assets/js/pages/moment-with-locales.min.js"></script>
<script src="../../../assets/plugins/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script src="../../../assets/js/pages/form-picker-custom.js"></script>

<!-- select插件 -->
<script src="../../../assets/plugins/select2/js/select2.full.min.js"></script>
<script src="../../../assets/plugins/multi-select/js/jquery.quicksearch.js"></script>
<script src="../../../assets/plugins/multi-select/js/jquery.multi-select.js"></script>
<script src="../../../assets/js/pages/form-select-custom.js"></script>

<!-- 表单验证提示 -->
<script src="../../../assets/js/jquery.tips.js"></script>

<script type="text/javascript">

var vm = new Vue({
	el: '#app',
	
	data:{
		PARENT_ID: '',				//上级ID
		${objectNameUpper}_ID: '',	//主键ID
		pd: [],						//存放字段参数
		pds: [],
	<#list fieldList as var>
		<#if var[3] == "是">
		<#if var[7] != 'null'>
		${var[0]}: '',
		</#if>
		</#if>
	</#list>
		msg:'add'
    },
	
	methods: {
		
        //初始执行
        init() {
        	var FID = this.getUrlKey('${objectNameUpper}_ID');	//链接参数
        	var P_ID = this.getUrlKey('PARENT_ID');
        	this.PARENT_ID = P_ID;
        	if(null != FID){
        		this.msg = 'edit';
        		this.${objectNameUpper}_ID = FID;
        		this.getData();
        	}else{
        		this.getGoAdd();
        	}
        	setTimeout(function(){
        		vm.getDict();
            },200);
        },
        
        //去保存
    	save: function (){
    		
			if(this.pd.NAME == '' | this.pd.NAME == undefined){
				$("#NAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				this.pd.NAME = '';
				this.$refs.NAME.focus();
				return false;
			}
    	<#list fieldList as var>
			<#if var[3] == "是">
			<#if var[7] != 'null'>
			if(this.${var[0]} == ''){
				$("#${var[0]}").tips({
					side:3,
		            msg:'请输入${var[2] }',
		            bg:'#AE81FF',
		            time:2
		        });
				this.pd.${var[0]} = '';
				this.$refs.${var[0]}.focus();
			return false;
			}
			<#elseif var[1] == 'Date'>
			this.pd.${var[0]} = $("#${var[0]}").val();
			if(this.pd.${var[0]} == '' || this.pd.${var[0]} == undefined){
				$("#${var[0]}").tips({
					side:3,
		            msg:'请输入${var[2] }',
		            bg:'#AE81FF',
		            time:2
		        });
				this.pd.${var[0]} = '';
				this.$refs.${var[0]}.focus();
			return false;
			}
			<#else>
			if(this.pd.${var[0]} == '' || this.pd.${var[0]} == undefined){
				$("#${var[0]}").tips({
					side:3,
		            msg:'请输入${var[2] }',
		            bg:'#AE81FF',
		            time:2
		        });
				this.pd.${var[0]} = '';
				this.$refs.${var[0]}.focus();
			return false;
			}
			</#if>
			</#if>
		</#list>
    		
    		$("#showform").hide();
    		$("#jiazai").show();
    		
            //发送 post 请求提交保存
            $.ajax({
	            	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'${objectNameLower}/'+this.msg,
			    	data: {${objectNameUpper}_ID:this.${objectNameUpper}_ID,
			    	PARENT_ID:this.PARENT_ID,
			    	NAME:this.pd.NAME,
				<#list fieldList as var>
					<#if var[3] == "是">
					<#if var[7] != 'null'>
					${var[0]}:this.${var[0]},
					<#else>
				    ${var[0]}:this.pd.${var[0]},
				    </#if>
					</#if>
				</#list>	
			    	tm:new Date().getTime()},
					dataType:"json",
					success: function(data){
                        if("success" == data.result){
                        	swal("", "保存成功", "success");
                        	setTimeout(function(){
                        		top.Dialog.close();//关闭弹窗
                            },1000);
                        }else if ("exception" == data.result){
                        	showException("${TITLE}",data.exception);//显示异常
                        	$("#showform").show();
                    		$("#jiazai").hide();
                        }
                    }
				}).done().fail(function(){
                   swal("登录失效!", "请求服务器无响应，稍后再试", "warning");
                   $("#showform").show();
           		   $("#jiazai").hide();
                });
    	},
    	
    	//根据主键ID获取数据(修改时)
    	getData: function(){
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'${objectNameLower}/goEdit',
		    	data: {${objectNameUpper}_ID:this.${objectNameUpper}_ID,tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                     if("success" == data.result){
                     	vm.pd = data.pd;							//参数map
                     	vm.pds = data.pds;
	                <#list fieldList as var>
						<#if var[3] == "是">
						<#if var[7] != 'null'>
						vm.${var[0]} = data.pd.${var[0]};
						<#elseif var[1] == 'Date'>
						$("#${var[0]}").val(data.pd.${var[0]});
						</#if>
						</#if>
					</#list>	
                     }else if ("exception" == data.result){
                     	showException("${TITLE}",data.exception);	//显示异常
                     	$("#showform").show();
                 		$("#jiazai").hide();
                     }
                  }
			}).done().fail(function(){
                  swal("登录失效!", "请求服务器无响应，稍后再试", "warning");
                  $("#showform").show();
          		  $("#jiazai").hide();
               });
    	},
    	
    	//根据主键ID获取数据(新增时)
    	getGoAdd: function(){
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'${objectNameLower}/goAdd',
		    	data: {${objectNameUpper}_ID:this.PARENT_ID,tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                       if("success" == data.result){
                       	 	vm.pds = data.pds;
                       }else if ("exception" == data.result){
	                       	showException("${TITLE}",data.exception);	//显示异常
	                       	$("#showform").show();
	                   		$("#jiazai").hide();
                       }
                   }
			}).done().fail(function(){
                  swal("登录失效!", "请求服务器无响应，稍后再试", "warning");
                  $("#showform").show();
          		  $("#jiazai").hide();
               });
    	},
    	
    	//获取数据字典数据
		getDict: function (){
<#list fieldList as var>
	<#if var[3] == "是">
		<#if var[1] == 'String'>
			<#if var[7] != 'null'>
				$.ajax({
					xhrFields: {
                    withCredentials: true
                	},
					type: "POST",
					url: httpurl+'dictionaries/getLevels?tm='+new Date().getTime(),
			    	data: {DICTIONARIES_ID:'${var[7]}'},
					dataType:'json',
					success: function(data){
						 $("#${var[0]}").append("<option value=''>请选择${var[2]}</option>");
						 $.each(data.list, function(i, dvar){
							 if(vm.${var[0]} == dvar.BIANMA){
							  	$("#${var[0]}").append("<option value="+dvar.BIANMA+" selected>"+dvar.NAME+"</option>");
							 }else{
								$("#${var[0]}").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
							 }
						 });
					}
				});
			</#if>
		</#if>
	</#if>
</#list>
		},
    	
    	//根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        }
        
	},
	
	mounted(){
        this.init();
    }
})

</script>
</body>
</html>