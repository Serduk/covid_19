package com.sserdiuk.covid_19

abstract class Presenter<V : ViewCallback, R : Router> {
    protected var view: V? = null
    protected var router: R? = null

    fun takeView(view: V) {
        if (this.view !== view) {
            this.view?.let { dropView() }
            this.view = view
            onTakeView()
        }
    }

    fun takeRouter(router: R) {
        if (this.router !== router) {
            this.router?.let { dropRouter() }
            this.router = router
            onTakeRouter()
        }
    }

    fun dropView() {
        if (this.view === view) {
            onDropView()
            this.view = null
        }
    }

    fun dropRouter() {
        if (this.router === router) {
            onDropRouter()
            this.router = null
        }
    }

    protected open fun onTakeView() = Unit
    protected open fun onDropView() = Unit
    protected open fun onTakeRouter() = Unit
    protected open fun onDropRouter() = Unit
}

interface ViewCallback
interface Router