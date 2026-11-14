import { Routes } from '@angular/router';
import { Accueil } from './components/accueil/accueil';
import { Patrimoines } from './components/patrimoines/patrimoines';
import { Circuits } from './components/circuits/circuits';
import { Evenements } from './components/evenements/evenements';
import { Reservations } from './components/reservations/reservations';

export const routes: Routes = [
  { path: '', component: Accueil },
  { path: 'patrimoines', component: Patrimoines },
  { path: 'circuits', component: Circuits },
  { path: 'evenements', component: Evenements },
  { path: 'reservations', component: Reservations },
  { path: '**', redirectTo: '' }
];