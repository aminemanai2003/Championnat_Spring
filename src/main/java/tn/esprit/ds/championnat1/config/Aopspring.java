package tn.esprit.ds.championnat1.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class Aopspring {

    // ============================================================
    // Pointcuts nommes
    // ============================================================

    /** Toutes les methodes de tous les services du package service */
    @Pointcut("execution(* tn.esprit.ds.championnat1.service.*.*(..))")
    public void coucheService() {}

    /** Methodes du service Equipe */
    @Pointcut("execution(* tn.esprit.ds.championnat1.service.IEquipeService.*(..))")
    public void methodesEquipeService() {}

    /** Methodes du service Sponsor */
    @Pointcut("execution(* tn.esprit.ds.championnat1.service.ISponsorService.*(..))")
    public void methodesSponsorService() {}

    /** Methodes du service Affectation (services avances) */
    @Pointcut("execution(* tn.esprit.ds.championnat1.service.IAffectationService.*(..))")
    public void methodesAffectationService() {}

    // ============================================================
    // @Before — journalisation avant l'execution de la methode
    // ============================================================

    @Before("coucheService()")
    public void logAvantExecution(JoinPoint joinPoint) {
        String methode = joinPoint.getSignature().getName();
        String classe  = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args  = joinPoint.getArgs();
        log.info("[BEFORE] Appel de {}.{} | Arguments : {}",
                classe, methode, Arrays.toString(args));
    }

    // ============================================================
    // @After — journalisation apres l'execution (succes ou erreur)
    // ============================================================

    @After("coucheService()")
    public void logApresExecution(JoinPoint joinPoint) {
        String methode = joinPoint.getSignature().getName();
        log.info("[AFTER]  Fin d'execution de la methode : {} — Execution terminee", methode);
    }

    // ============================================================
    // @AfterReturning — journalisation de la valeur retournee
    // ============================================================

    @AfterReturning(pointcut = "methodesEquipeService()", returning = "resultat")
    public void logRetourEquipeService(JoinPoint joinPoint, Object resultat) {
        String methode = joinPoint.getSignature().getName();
        log.info("[AFTER_RETURNING] EquipeService.{} -> Valeur retournee : {}",
                methode, resultat);
    }

    @AfterReturning(pointcut = "methodesSponsorService()", returning = "resultat")
    public void logRetourSponsorService(JoinPoint joinPoint, Object resultat) {
        String methode = joinPoint.getSignature().getName();
        log.info("[AFTER_RETURNING] SponsorService.{} -> Valeur retournee : {}",
                methode, resultat);
    }

    @AfterReturning(pointcut = "methodesAffectationService()", returning = "resultat")
    public void logRetourAffectationService(JoinPoint joinPoint, Object resultat) {
        String methode = joinPoint.getSignature().getName();
        log.info("[AFTER_RETURNING] AffectationService.{} -> Valeur retournee : {}",
                methode, resultat);
    }

    // ============================================================
    // @AfterThrowing — journalisation des exceptions
    // ============================================================

    @AfterThrowing(pointcut = "coucheService()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methode = joinPoint.getSignature().getName();
        log.error("[AFTER_THROWING] Exception dans {} | Type : {} | Message : {}",
                methode,
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }

    // ============================================================
    // @Around — mesure du temps d'execution de chaque methode service
    // ============================================================

    @Around("coucheService()")
    public Object mesurerTempsExecution(ProceedingJoinPoint pjp) throws Throwable {
        String methode = pjp.getSignature().getName();
        long debut = System.currentTimeMillis();
        log.info("[AROUND]  Debut de l'execution : {}", methode);
        try {
            Object resultat = pjp.proceed();
            long duree = System.currentTimeMillis() - debut;
            log.info("[AROUND]  Fin de l'execution : {} | Duree : {} ms", methode, duree);
            return resultat;
        } catch (Throwable t) {
            long duree = System.currentTimeMillis() - debut;
            log.error("[AROUND]  Exception dans {} apres {} ms", methode, duree);
            throw t;
        }
    }
}
