<?php session_start();
    if(!isset($_SESSION['id']))
    {
        header("Location: http://localhost:80/backend_GEN/web/home.php");
        exit();
    }
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>A Simple Page with CKEditor</title>
        <!-- Make sure the path to CKEditor is correct. -->
        <script type="text/javascript" src="ckeditor/ckeditor.js"></script>
        <style>
            body{
                background-image:url('images/bg.jpg');
            }
            div#editor,textArea{
               padding:10px;
               height:80%;
               position: relative;
            }
            div#controls{
                padding:20px;
            }
            .smallbt {
                background-color:white;
                border:2px solid rgba(22, 111, 212, 0.89);
                border-radius:10px;
                cursor:pointer;
                font-family:Arial;
                font-size:17px;
                text-decoration:none;
                text-shadow:0px 1px 0px #2f6627;
            }
            .smallbt:hover {
                    background-color: rgba(22, 111, 212, 0.89);
                    color:white;
            }
            .smallbt:active {
                    position:relative;
                    top:4px;
            }
        </style>
    </head>
    <body>
        <form action="http://localhost:8080/backend_GEN/buildForm" enctype="multipart/form-data">
            <div id="editor">
                <textarea name="editor1" id="editor1" rows="120" cols="80" placeholder="Editor contents here">
                </textarea>
            </div>
            <div id="controls">
                <a href="home.php"><input type="button" style="padding:20px 40px;" class="smallbt" value="<<"/></a>
                <input type="submit" style="padding:20px 40px;" class="smallbt" value="submit"/>
            </div>
            <script>
                var editor = CKEDITOR.replace('editor1');
                /*editor.on('change', function (evt) {
                 // getData() returns CKEditor's HTML content.
                 //console.log('Total bytes: ' + evt.editor.getData());
                 document.getElementById("newd").innerHTML=evt.editor.getData();    
                 });*/
            </script>
            <!--script type="text/javascript">
                // Need to wait for the ckeditor instance to finish initialization
                // because CKEDITOR.instances.editor.commands is an empty object
                // if you try to use it immediately after CKEDITOR.replace('editor');
                CKEDITOR.plugins.registered['save'] = {
                    init: function (editor) {
                        CKEDITOR.on('instanceReady', function (ev) {

                            // Create a new command with the desired exec function
                            var overridecmd = new CKEDITOR.command(editor, {
                                modes: {wysiwyg: 1, source: 1},
                                exec: function (editor) {
                                    // Replace this with your desired save button code
                                    alert(editor.document.getBody().getHtml());
                                }
                            });

                            // Replace the old save's exec function with the new one
                            ev.editor.commands.save.exec = overridecmd.exec;
                    });
                    editor.ui.addButton('Save', { label: 'Save', command: 'save' });
                }
                
            };

                    CKEDITOR.replace('editor1');

            </script-->
            <!--script type="text/javascript" >
                CKEDITOR.plugins.registered['save'] = {
      init: function (editor) {
         var command = editor.addCommand('save',
         {
              modes: { wysiwyg: 1, source: 1 },
              exec: function (editor) { // Add here custom function for the save button
              alert('want to save You clicked the save button in CKEditor toolbar!');
              }
         });
         editor.ui.addButton('Save', { label: 'Save', command: 'save' });
      } 
  };
                // Replace the <textarea id="editor1"> with a CKEditor
                // instance, using default configuration.
                CKEDITOR.replace( 'editor1' );
            </script-->
        </form>
    </body>
</html>