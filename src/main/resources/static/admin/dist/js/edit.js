var blogEditor;

$("#blogTags").tagsInput({
	width:'100%',
	height:'38px',
	defaultText: '文章标签'
});

$('.select2').select2()


$(function(){
	blogEditor = editormd("blog-editormd", {
        width: "100%",
        height: 640,
        syncScrolling: "single",
        path: "/admin/plugins/editormd/lib/",
        toolbarModes: 'full',
        /**图片上传配置*/
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"], //图片上传格式
        imageUploadURL: "/admin/news/md/uploadfile",
        onload: function (obj) { //上传成功之后的回调
        }
    });
	
	new AjaxUpload("#uploadCoverImage",{
		action:"/admin/upload/file",
		name:'file',
		autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#blogCoverImage").attr("src", r.data);
                $("#blogCoverImage").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
        
	});
});

$('#randomCoverImage').click(function(){
	var rand = parseInt(Math.random()*40+1);
	$("#blogCoverImage").attr("src", '/admin/dist/img/rand/' + rand + ".jpg");
    $("#blogCoverImage").attr("style", "width:160px ;height: 120px;display:block;");
});



$('#confirmButton').click(function () {
	var blogId = $('#blogId').val();
    var blogTitle = $('#blogName').val();
    var blogSubUrl = $('#blogSubUrl').val();
    var blogCategoryId = $('#blogCategoryId').val();
    var blogTags = $('#blogTags').val();
    var blogContent = blogEditor.getMarkdown();
    var blogCoverImage = $('#blogCoverImage')[0].src;
    var blogStatus = $("input[name='blogStatus']:checked").val();
    var enableComment = $("input[name='enableComment']:checked").val();
    if (isNull(blogTitle)) {
        swal("请输入文章标题", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTitle, 150)) {
        swal("标题过长", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogSubUrl, 150)) {
        swal("路径过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogCategoryId)) {
        swal("请选择文章分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogTags)) {
        swal("请输入文章标签", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTags, 150)) {
        swal("标签过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogContent)) {
        swal("请输入文章内容", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTags, 100000)) {
        swal("文章内容过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogCoverImage) || blogCoverImage.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/news/addNew';
    var data = {
        "blogTitle": blogTitle, "blogSubUrl": blogSubUrl, "blogCategoryId": blogCategoryId,
        "blogTags": blogTags, "blogContent": blogContent, "blogCoverImage": blogCoverImage, "blogStatus": blogStatus,
        "enableComment": enableComment
    };
    
    if (blogId > 0) {
        url = '/admin/news/update';
        swlMessage = '修改成功';
        data = {
            "blogId": blogId,
            "blogTitle": blogTitle,
            "blogSubUrl": blogSubUrl,
            "blogCategoryId": blogCategoryId,
            "blogTags": blogTags,
            "blogContent": blogContent,
            "blogCoverImage": blogCoverImage,
            "blogStatus": blogStatus,
            "enableComment": enableComment
        };
    }
    
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
                swal("保存成功", {
                    icon: "success"
                });
            }
            else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});


$('#cancelButton').click(function () {
    window.location.href = "/admin/blogs";
});





/*//Initialize Select2 Elements
$('.select2').select2();
//KindEditor变量
var editor;

$(function () {
    $('.alert-danger').css("display", "none");

    //详情编辑器
    editor = KindEditor.create('textarea[id="editor"]', {
        items: ['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'multiimage',
            'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
            'anchor', 'link', 'unlink'],
        uploadJson: '/admin/upload/file',
        filePostName: 'file'
    });

    new AjaxUpload('#uploadCoverImage', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#newsCoverImage").attr("src", r.data);
                $("#newsCoverImage").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

$('#confirmButton').click(function () {
    var newsTitle = $('#newsTitle').val();
    var categoryId = $('#newsCategoryId').val();
    var newsContent = editor.html();
    if (isNull(newsTitle)) {
        swal("请输入文章标题", {
            icon: "error",
        });
        return;
    }
    if (!validLength(newsTitle, 150)) {
        swal("标题过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(categoryId)) {
        swal("请选择文章分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(newsContent)) {
        swal("请输入文章内容", {
            icon: "error",
        });
        return;
    }
    if (!validLength(newsContent, 100000)) {
        swal("文章内容过长", {
            icon: "error",
        });
        return;
    }
    $('#articleModal').modal('show');
});

$('#saveButton').click(function () {
    var newsId = $('#newsId').val();
    var newsTitle = $('#newsTitle').val();
    var newsCategoryId = $('#newsCategoryId').val();
    var newsContent = editor.html();
    var newsCoverImage = $('#newsCoverImage')[0].src;
    var newsStatus = $("input[name='newsStatus']:checked").val();
    if (isNull(newsCoverImage) || newsCoverImage.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/news/addNew';
    var swlMessage = '保存成功';
    var data = {
        "newsTitle": newsTitle,  "newsCategoryId": newsCategoryId,
        "newsContent": newsContent, "newsCoverImage": newsCoverImage, "newsStatus": newsStatus
    };
    if (newsId > 0) {
        url = '/admin/news/update';
        swlMessage = '修改成功';
        data = {
            "newsId": newsId,
            "newsTitle": newsTitle,
            "newsCategoryId": newsCategoryId,
            "newsContent": newsContent,
            "newsCoverImage": newsCoverImage,
            "newsStatus": newsStatus
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回资讯列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/news";
                })
            }
            else {
                $('#articleModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败了", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/news";
});
*/