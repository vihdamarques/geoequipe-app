var AppUtil = {
    getUserId: function (success, fail) {
      return cordova.exec(success, fail, "AppUtil", "getUserId", []);
    }
   ,setUserId: function (userId) {
      return cordova.exec(null, null, "AppUtil", "setUserId", [userId]);
    }
   ,getEnviaSinal: function (success, fail) {
      return cordova.exec(success, fail, "AppUtil", "getEnviaSinal", []);
    }
   ,setEnviaSinalOn: function (success, fail) {
      return cordova.exec(success, fail, "AppUtil", "setEnviaSinalOn", []);
    }
   ,setEnviaSinalOff: function (success, fail) {
      return cordova.exec(success, fail, "AppUtil", "setEnviaSinalOff", []);
    }
   ,openMaps: function (lat, lng) {
      return cordova.exec(null, null, "AppUtil", "openMaps", [lat, lng]);
    }
   ,updateNotification: function () {
      return cordova.exec(null, null, "AppUtil", "updateNotification", []);
    }
  ,encrypt: function (data, success, fail) {
      return cordova.exec(success, fail, "AppUtil", "encrypt", [data]);
   }
};

function login() {
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
                           AppUtil.setUserId(data.id_usuario);
                           AppUtil.updateNotification();
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
  AppUtil.setUserId("");
  AppUtil.updateNotification();
  navigator.app.exitApp();
  //$.mobile.changePage("login.html");
}

$(document).on("pageshow", "#configuracoes", function() {
  $("#enviaSinal").on("change", function() {
    if ($(this).val() == "on")
      AppUtil.setEnviaSinalOn(null, null);
    else
      AppUtil.setEnviaSinalOff(null, null);
  });
  AppUtil.getEnviaSinal(function(retorno) {
    $('#enviaSinal').val(retorno);
    $('#enviaSinal').slider('refresh');
  }, function(){});
});

$(document).on("pageshow", "#lista_tarefas", function() {
  carregaTarefas();
});

function init() {
  AppUtil.getUserId(function(retorno) {
    if (!retorno)
      $.mobile.changePage("login.html");
    else
      $.mobile.changePage("lista_tarefas.html");
   setTimeout(navigator.splashscreen.hide, 2000);
  }, function(){});
  document.addEventListener("backbutton", onBackKey, false);
}

function onBackKey() {
  if ($("#configuracoes").size())
    $.mobile.changePage("lista_tarefas.html");
  else
    navigator.app.exitApp();
}

function carregaTarefas() {
  var listatarefas = "";

  $.ajax({type: 'GET'
         ,url: 'http://geoequipe.aws.af.cm/tarefa/consulta/' + (AppUtil.getUserId() || '0')
         ,dataType: 'json'
         ,async: true
         ,success: function(data) {
                     if (data) {
                       if (data.erro && data.erro.length > 0) {
                         listatarefas = data.erro;
                       } else {
                         if (data.tarefas.length > 0) {
                           //popula variavel com as tarefas
                           for (i=0; i<data.tarefas.length; i++) {
                             listatarefas += 
                             '<div data-role="collapsible" data-collapsed="false">' +
                               '<h3>';
                                 if (data.tarefas[i].apontamento) {
                                   listatarefas += '<img style="width: 16px; height: 16px" src="img/ok.png">';
                                 }
                                 listatarefas += data.tarefas[i].local +
                               '</h3>' +
                               '<font size="2px">' + data.tarefas[i].descricao + '</font>' +
                               '<input type="hidden" name="id_tarefa" id="id_tarefa" value="' + data.tarefas[i].id_tarefa + '"' +
                               '<br>' +
                               '<textarea name="apontamento" id="apontamento" placeholder="Apontamento" cols="200">' + data.tarefas[i].apontamento + '</textarea>' +
                               '<div class="ui-grid-a">' +
                                 '<div class="ui-block-a">';
                                   if (!data.tarefas[i].apontamento) {
                                     listatarefas += '<input type="submit" data-inline="true" data-theme="b" data-icon="check" data-iconpos="left" value="Concluir" data-mini="true" onclick="concluirTarefa('+data.tarefas[i].id_tarefa+');">';
                                   }
                                 listatarefas += 
                                 '</div>' +
                                 '<div class="ui-block-b">' +
                                   '<a href="javascript:;" onclick="AppUtil.openMaps(\'' + data.tarefas[i].coord.lat + '\',\'' + data.tarefas[i].coord.lng + '\')">' +
                                     '<div style=" text-align:right">' +
                                       '<img style="width: 40px; height: 40px" src="img/google-maps-icone.png">' +
                                     '</div>' +
                                   '</a>' +
                                 '</div>' +
                               '</div>' +
                             '</div>';
                           }
                         } else {
                           listatarefas = '<div data-role="collapsible" data-collapsed="false"><h3>Não há tarefas hoje.</h3></div>';
                         }
                       }
                     } else {
                       listatarefas = '<div data-role="collapsible" data-collapsed="false"><h3>Não há dados!</h3></div>';
                     }
                     $("#tarefas").html(listatarefas).trigger('create');
          }
         ,error: function (xhr, ajaxOptions, thrownError) {
                   alert("Erro ao fazer consulta de tarefa: " + xhr.status + "\n" +
                         "Mensagem: " + xhr.statusText + "\n" +
                         "Resposta: " + xhr.responseText + "\n" + thrownError);
          }
  });
}

function concluirTarefa(id_tarefa) {
  var apontamento = $("#apontamento").val();
  if (!apontamento) {
    alert('Insira um apontamento.');
  } else {
    var dados = {};
    dados.apontamento = apontamento;
    dados.id_usuario  = AppUtil.getUserId();
    dados.id_tarefa   = id_tarefa;
    dados = AppUtil.encrypt(JSON.stringify(dados)).replace(/\+/g,'-').replace(/\//g,'_');

    $.ajax({type: 'GET'
           ,url: 'http://geoequipe.aws.af.cm/tarefa/concluir/' + dados
           ,dataType: 'json'
           ,success: function(data) {
                       if (data) {
                         if (data.erro && data.erro.length > 0) {
                           alert(data.erro);
                         } else {
                           alert("Tarefa concluida com sucesso!");
                           carregaTarefas();
                         }
                       } else {
                         alert("Erro ao fazer login!");
                       }
            }
           ,error: function (xhr, ajaxOptions, thrownError) {
                alert("Erro ao fazer concluir tarefa: " + xhr.status + "\n" +
                       "Mensagem: " + xhr.statusText + "\n" +
                       "Resposta: " + xhr.responseText + "\n" + thrownError);
            }
    });
  }
}