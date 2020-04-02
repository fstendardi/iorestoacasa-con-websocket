$(function () {
  new Vue({
    el: '#app',
    data: {
      items: [],
      perPage: 30,
      currentPage: 1,
      rows: 0,
      sortBy: null,
      sortDesc: false,
      fields: [{
    	  key: 'denominazioneProvincia',
    	  sortable: true
      },
      {
    	  key: 'siglaProvincia',
    	  sortable: true
      },
      {
    	  key: 'totaleCasi',
    	  sortable: true
      },
      {
    	  key: 'totaleGuariti',
    	  sortable: true
      },
      {
    	  key: 'totaleDecessi',
    	  sortable: true
      },
      {
    	  key: 'dataModifica',
    	  sortable: true
      }]
    },
    mounted: function () {
    	var self = this;
    	this.initWebSocket();
    	this.sortBy = this.fields[0].key;
    	this.loadData();
    },
    watch: {
    	
    },
    
    methods: {
      sortChanged: function(ctx){
    	  
    	  this.loadData(null, ctx.sortBy, ctx.sortDesc);
      },
      loadData: function(page, sort, sortDesc){
    	    
    	  var self = this;
    	  page = page || self.currentPage;
    	  sort = sort || 'denominazioneProvincia';
    	  
    	  sortDesc = sortDesc? 'DESC': 'ASC'
    	  $.get('/api/covid', {
      		page: page - 1,
      		size: self.perPage,
      		sort: sort+','+sortDesc
      	}).then(function(response){
      		self.items = response.content;
      		self.rows = response.totalElements;
      		
      	});  
      },
      initWebSocket: function(){
    	  var self = this;
    	  this.wsClient = initWebsocketClient();
    	  
    	  this.wsClient.connect({}, function(){
    		 self.wsClient.subscribe('/user/admin/covid.segnalazioni', function(message){
    			 var segnalazione = JSON.parse(message.body);
    			 var variant = segnalazione.status == 'GUARITO' ? 'success' : 'danger';
    			 var content = segnalazione.status == 'GUARITO' ? 'Un soggetto e\' appena guarito! :)' : 'Nuova positivita\' segnalata :(';
    			 
    			 self.$bvToast.toast(content, {
    				 solid: true,
    				 variant: variant,
    				 noCloseButton: true
    			 });
    		 });
    	  });
    	  
      }
    }
  });



})