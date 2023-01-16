package tuver.sudokusolver.di.impl

import tuver.sudokusolver.di.AppModule
import tuver.sudokusolver.di.DomainModule

class AppModuleImpl(
    domainModule: DomainModule = DomainModuleImpl()
) : AppModule,
    DomainModule by domainModule