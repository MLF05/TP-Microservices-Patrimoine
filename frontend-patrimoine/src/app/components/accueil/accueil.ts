import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { PatrimoineService } from '../../services/patrimoine.service';
import { CircuitService } from '../../services/circuit.service';
import { EvenementService } from '../../services/evenement.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-accueil',
  imports: [RouterLink, DatePipe],
  templateUrl: './accueil.html',
  styleUrl: './accueil.css'
})
export class Accueil implements OnInit {
  private patrimoineService = inject(PatrimoineService);
  private circuitService = inject(CircuitService);
  private evenementService = inject(EvenementService);
  private reservationService = inject(ReservationService);

  chargement = signal(true);
  horsLigne = signal(false);
  patrimoines = signal<any[]>([]);
  circuits = signal<any[]>([]);
  evenements = signal<any[]>([]);
  reservations = signal<any[]>([]);

  stats = computed(() => [
    { icone: 'fa-landmark', valeur: this.patrimoines().length, label: 'Sites patrimoniaux' },
    { icone: 'fa-route', valeur: this.circuits().length, label: 'Circuits de visite' },
    { icone: 'fa-calendar-alt', valeur: this.evenements().length, label: 'Événements' },
    { icone: 'fa-ticket-alt', valeur: this.reservations().length, label: 'Réservations' }
  ]);

  // Événements à venir ; à défaut, les derniers événements enregistrés
  evenementsAffiche = computed(() => {
    const aujourdhui = new Date().toISOString().slice(0, 10);
    const tous = [...this.evenements()].sort((a, b) => String(a.dateDebut).localeCompare(String(b.dateDebut)));
    const avenir = tous.filter(e => String(e.dateFin ?? e.dateDebut) >= aujourdhui);
    return (avenir.length ? avenir : tous.reverse()).slice(0, 3);
  });

  patrimoinesAffiches = computed(() => this.patrimoines().slice(0, 3));

  ngOnInit(): void {
    const sur = (o: Observable<any[]>) => o.pipe(catchError(() => of(null)));
    forkJoin({
      p: sur(this.patrimoineService.getAll()),
      c: sur(this.circuitService.getAll()),
      e: sur(this.evenementService.getAll()),
      r: sur(this.reservationService.getAll())
    }).subscribe(res => {
      this.horsLigne.set(!res.p && !res.c && !res.e && !res.r);
      this.patrimoines.set(res.p ?? []);
      this.circuits.set(res.c ?? []);
      this.evenements.set(res.e ?? []);
      this.reservations.set(res.r ?? []);
      this.chargement.set(false);
    });
  }

  tauxRemplissage(e: any): number {
    return e.capaciteMaximale ? Math.round((100 * (e.placesReservees || 0)) / e.capaciteMaximale) : 0;
  }
}