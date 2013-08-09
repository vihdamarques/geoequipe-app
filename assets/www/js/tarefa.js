function fnc_tarefa() {

var v_tarefas = 	{	"tarefas":[{
						"id_tarefa":"1",
						"descricao":"Está tudo dando errado, Dona francisca não sabe mais o que fazer.",
						"local":"Loja da Francisca",
						"apontamento":"O equipamento estava fora da tomada. Mulher burra pra caramba.",
						"coord":
						{
							"lat":-23.123,
							"lng":-46.123
						}
					},
					{
						"id_tarefa":"2",
						"descricao":"A independência da comissão criada nesta sexta-feira (9) para investigar o suposto cartel envolvendo o metrô e os trens metropolitanos de São Paulo foi questionada por integrantes do grupo já anunciado. Montada para ser uma comissão externa, formada por órgãos da sociedade civil, ela será regida pela Corregedoria Geral de Administração, segundo anunciou hoje o Governo de São Paulo.",
						"local":"Pizzaria",
						"apontamento":"",
						"coord":
						{
							"lat":-23.456,
							"lng":-46.456
						}
					},
					{
						"id_tarefa":"2",
						"descricao":"Antes do anúncio, Claudio Abramo, diretor-executivo da Transparência Brasil, um dos órgãos da comissão, comemorou a independência da comissão aparentemente sem saber que ela seria encabeçada pela Corregedoria. O governo do Estado toma uma medida que não envolve participantes do governo. Essa comissão não tem representantes do governo. Então, a influência que o governo possa ter nessas decisões não existe dentro desse contexto, disse ele. ",
						"local":"Borracharia Barbosa",
						"apontamento":"",
						"coord":
						{
							"lat":-23.456,
							"lng":-46.456
						}
					},
					{
						"id_tarefa":"3",
						"descricao":"No entanto, depois de Úngaro ser anunciado, Abramo confrontou o próprio governador sobre a independência do grupo. Irônico, o diretor-executivo da Transparência Brasil perguntou se não seriam só grupos da sociedade civil que participariam da comissão e questionou a presença do corregedor, que poderia filtrar as informações passadas ao grupo segundo interesse do governo.",
						"local":"Pizzaria Boi na Brasa",
						"apontamento":"",
						"coord":
						{
							"lat":-23.798,
							"lng":-46.789
						}
					}
					],
					"erro":"Erro ao carregar tarefas."
				};
				
	listatarefas = '';
	
	for (i=0; i<v_tarefas.tarefas.length; i++) {
	
		listatarefas += '<div data-role="collapsible" data-collapsed="false">' +
                '<h3>' +
                    v_tarefas.tarefas[i].local +
                '</h3>' +
				'<font size="2px">' + v_tarefas.tarefas[i].descricao + '</font>' +
				'<input type="hidden" name="id_tarefa" id="id_tarefa" value="' + v_tarefas.tarefas[i].id_tarefa + '"' +
				'<br>' +
				'<textarea name="" id="apontamento" placeholder="Apontamento" cols="200">' + v_tarefas.tarefas[i].apontamento + '</textarea>' +
				'<div class="ui-grid-a">' +
                    '<div class="ui-block-a">' +
						'<input type="submit" data-inline="true" data-theme="b" data-icon="check" data-iconpos="left" value="Concluir" data-mini="true">' +
					'</div>' +
					'<div class="ui-block-b">' +
						'<a href="geo:' + v_tarefas.tarefas[i].coord.lat + ',' + v_tarefas.tarefas[i].coord.lng + '">' +
							'<div style=" text-align:right">' +
								'<img style="width: 40px; height: 40px" src="img\google-maps-icone.png">' +
							'</div>' +
						'</a>' +
					'</div>' +
				'</div>' +
            '</div>';
		
	}
	return listatarefas;
}