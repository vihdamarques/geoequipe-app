var AppConfig = {
    getUserId: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "getUserId", []);
    }
   ,setUserId: function (userId) {
      return cordova.exec(null, null, "AppConfig", "setUserId", [userId]);
    }
   ,getEnviaSinal: function (success, fail) { 
      return cordova.exec(success, fail, "AppConfig", "getEnviaSinal", []);
    }
   ,setEnviaSinalOn: function (success, fail) { 
      return cordova.exec(success, fail, "AppConfig", "setEnviaSinalOn", []);
    }
   ,setEnviaSinalOff: function (success, fail) { 
      return cordova.exec(success, fail, "AppConfig", "setEnviaSinalOff", []);
    }
};

function fnc_login() {
  $.ajax({type: 'GET'
         ,url: 'http://geoequipe.aws.af.cm/login/' + $("#usuario").val() + '/' + $("#senha").val()
         ,dataType: 'json'
         ,success: function(data) {
                     if (data) {
                       if (data.erro && data.erro.length > 0) {
                         alert(data.erro);
                       } else {
                         if (data.id_usuario && data.id_usuario.length > 0) {
                           // save in local storage
                           AppConfig.setUserId(data.id_usuario);
                           // perform page transition 
                           $.mobile.changePage("lista_tarefas.html");
                         }
                       }
                     } else {
                       alert("Erro ao fazer login!");
                     }
          }
         ,error: function (xhr, ajaxOptions, thrownError) {
              alert("Erro ao fazer login: " + xhr.status + "\n" +
                     "Mensagem: " + xhr.statusText + "\n" +
                     "Resposta: " + xhr.responseText + "\n" + thrownError);
          }
      });
}

function logout() {
  AppConfig.setUserId("");
  $.mobile.changePage("index.html");
}