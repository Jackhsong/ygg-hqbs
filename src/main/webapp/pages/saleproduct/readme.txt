indexhtml.ftl 和此目录下的有html结束的两个文件，都是用freemarker生成静态的html加入到内存缓存中，以此文件为主。
因freemarker 生成静态的 html时，页面中必须要注入变量，不能有没有注入的变量值，否则报错，eg:$(rc.contextPath} 能直接在.ftl中使用
但是在 ...html.ftl文件中不能使用，没有模型给此变量注入值。
