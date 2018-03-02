(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('acta', {
            parent: 'entity',
            url: '/acta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.acta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acta/actas.html',
                    controller: 'ActaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('acta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('acta-detail', {
            parent: 'acta',
            url: '/acta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.acta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acta/acta-detail.html',
                    controller: 'ActaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('acta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Acta', function($stateParams, Acta) {
                    return Acta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'acta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('acta-detail.edit', {
            parent: 'acta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acta/acta-dialog.html',
                    controller: 'ActaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acta', function(Acta) {
                            return Acta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acta.new', {
            parent: 'acta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acta/acta-dialog.html',
                    controller: 'ActaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                folio: null,
                                fechatomagrado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('acta', null, { reload: 'acta' });
                }, function() {
                    $state.go('acta');
                });
            }]
        })
        .state('acta.edit', {
            parent: 'acta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acta/acta-dialog.html',
                    controller: 'ActaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acta', function(Acta) {
                            return Acta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('acta', null, { reload: 'acta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acta.delete', {
            parent: 'acta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acta/acta-delete-dialog.html',
                    controller: 'ActaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Acta', function(Acta) {
                            return Acta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('acta', null, { reload: 'acta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
