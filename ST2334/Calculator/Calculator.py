from re import S
from tabnanny import verbose
from scipy.stats import binom, nbinom, poisson, expon, norm, t, chi2, f, tvar
import numpy as np

def sqrt(x):
    return x**0.5

def variance(sample):
    return tvar(np.array(sample))

def pooled_variance(sample1, sample2):
    n1 = len(sample1)
    n2 = len(sample2)
    var1 = variance(sample1)
    var2 = variance(sample2)
    
    return ((n1 - 1) * var1 + (n2 - 1) * var2) / (n1 + n2 - 2)

def mean(sample):
    return np.array(sample).mean()

class Distribution:
    def __init__(self, name, distribution):
        self.name = name
        self.distribution = distribution

    def pdf(self, x, verbose=True):
        temp = self.distribution.pmf(x)
        if verbose: print("P({} = {:.4f}) = {:.4f}\n".format(self.name, x, temp))
        return temp

    def geqCDF(self, x, verbose=True):
        temp = 1 - self.distribution.cdf(x)
        if verbose: print("P({} >= {:.4f}) = {:.4f}\n".format(self.name, x, temp))
        return temp

    def leqCDF(self, x, verbose=True):
        temp = self.distribution.cdf(x)
        if verbose: print("P({} <= {:.4f}) = {:.4f}\n".format(self.name, x, temp))
        return temp
    
    def rangeCDF(self, a, b, verbose=True):
        temp = self.distribution.cdf(b) - self.distribution.cdf(a) 
        if verbose: print("P({:.4f} <= {} <= {:.4f}) = {}\n".format(a, self.name, b, temp))
        return temp

    def mean(self, verbose=True):
        temp = self.distribution.mean(x)
        if verbose: print("E({}) = {:.4f}\n".format(self.name, temp))
        return temp

    def variance(self, verbose=True):
        temp = self.distribution.var(x)
        if verbose: print("Var({}) = {:.4f}".format(self.name, temp))
        return temp


class Binomial(Distribution):
    """
    Returns a random variable following a binomial distribution with parameters n and p.
    i.e. X ~ B(n, p)
    
    n: The number of successive independent Bernouilli trials performed
    p: The probability of success for each trial

    """
    def __init__(self, n, p, name="X"):
        super().__init__(name, binom(n, p))
        self.n = n
        self.p = p
        print("{} ~ B({:.4f}), {:.4f}".format(name, n, p))

    def __str__(self) -> str:
        return "{} ~ B({}, {})".format(self.name, self.n, self.p)

class NegativeBinomial(Distribution):
    """
    Returns a random variable following a negative binomial distribution with parameters k and p.
    i.e. X ~ NB(k, p)

    Suppose that our experiment has all the properties of a binomial experiment.
    But instead, we repeat the trials until a fixed number of successes has occurred.
    We are interested in the probability that the k-th success occurs on the $-th trial.
    Observe that we are now interested in the number of trials needed to obtain k successes, while in the binomial case, 
    we are interested in the number of successes in n trials.

    k: Number of successes desired
    p: Probability of success of each Bernouili trial

    """
    def __init__(self, k, p, name="X"):
        super().__init__(name, nbinom(k, p))
        self.k = k
        self.p = p
        print("{} ~ NB({:.4f}), {:.4f}".format(name, k, p))

    def pdf(self, x, verbose=False):
        temp = super().pdf(x - self.k, verbose=False)
        if verbose: print("P(X = {}) = {}\n".format(x, temp))
        return temp

    def cdf(self, x, verbose=False):
        temp = super().leqCDF(x - self.k, verbose=False)
        if verbose: print("P(X <= {}) = {}\n".format(x, temp))
        return temp

    def __str__(self) -> str:
        return "{} ~ NB({}, {})".format(self.name, self.k, self.p)


class Poisson(Distribution):
    """
    Returns a random variable following a poisson distribution with parameter l.
    i.e. X ~ P(l)

    """
    def __init__(self, l, name= "X"):
        super().__init__(name, poisson(l))
        self.l = l
        print("{} ~ poisson({:.4f})".format(name, l))

    def __str__(self) -> str:
        return "{} ~ P({})".format(self.name, self.l)

class Exponential(Distribution):
    """
    Returns a random variable following an exponential distribution with parameter alpha.
    Such that f(x) = a * exp(-a*x)
    i.e. X ~ Exp(alpha)

    alpha: 1/mean

    """
    def __init__(self, alpha, name="X"):
        super().__init__(name, expon(alpha))
        self.alpha = alpha
        print("{} ~ Exp({:.4f})".format(name, alpha))
    def __str__(self) -> str:
        return "{} ~ Exp({})".format(self.name, self.alpha)

class Normal(Distribution):
    """
    Returns a random variable following a normal distribution with parameters mu and sigma.
    i.e. X ~ N(mu, sigma^2)

    mu: Mean of the distribution
    sigma: Standard deviation of the distribution

    """
    def __init__(self, mu=0, sigma=1, name="X"):
        super().__init__(name, norm(mu, sigma))
        self.mu = mu
        self.sigma = sigma
        print("{} ~ N({:.4f}, {:.4f}^2)".format(name, mu, sigma))

    def statistic(xbar, mu, sigma, verbose = True):
        temp = (xbar - mu) / sigma
        if verbose:
            print("Z = ({:.4f} - {:.4f}) / {:.4f} = {:.4f}".format(xbar, mu, sigma, temp))
        return temp
    
    def interval(self, confidence, verbose=True):
        temp = self.distribution.interval(confidence)
        if verbose:
            print("The {}% CI for the population mean based on {} is {}".format(confidence*100, self.name, temp))
        return temp

    def inverse(alpha, verbose=True):
        """
        Returns the value of z statistic such that P(Z < z) = 1-alpha
        Equivalently, its is the z-statstic such that P(Z\ > z) = alpha
        """
        temp = norm(0, 1).ppf(1-alpha)
        if verbose: 
            print("P(Z > {:.4f}) = {:.4f}".format(temp, alpha))
        return temp
        
    def __str__(self) -> str:
        return "{} ~ N({}, {}^2)".format(self.name, self.mu, self.sigma)

class T(Distribution):
    """
    Returns a random variable following a t distribution with n degrees of freedom.
    i.e. T ~ t(n)

    n: degrees of freedom

    """
    def __init__(self, n, name="X"):
        super().__init__(name, t(n))
        self.n = n
        print("{} ~ t({:.4f})".format(name, n))
    
    def statistic(xbar, mu, S, n, verbose = True):
        temp = (xbar - mu) / (S / n**0.5)
        if verbose:
            print("T = ( {:.4f} - {:.4f} ) / ( {:.4f} / sqrt({:.4f} )) = {:.4f},".format(xbar, mu, S, n, temp))
        return temp

    def inverse(self, alpha, verbose=True):
        """
        Returns the t statistic such that P(T < t) = 1-alpha
        """

        temp = t(self.n).ppf(1-alpha)
        if verbose:
            print("For {} degrees of freedom,".format(self.n)) 
            print("P(T < {:.4f}) = {:.4f}".format(temp, 1-alpha))
        return temp

    def __str__(self) -> str:
        return "{} ~ t({})".format(self.name, self.n)

class Chi2(Distribution):
    """
    Returns a random variable following a chi-squared distribution with n degrees of freedom.
    i.e. X ~ Chi^2(n)

    n: degrees of freedom

    """
    def __init__(self, n, name="X"):
        super().__init__(name, chi2(n))
        self.n = n
        print("{} ~ Chi2({:.4f})".format(name, n))
    
    def statistic(n, S, sigma, verbose=True):
        temp =  ((n-1) * S**2) / sigma**2
        if verbose:
            print("Chi2 = ( ({}-1) * {}^2 ) / {}^2 = {:.4f},".format(n, S, sigma, temp))
        return temp
        

    def inverse(self, alpha, verbose=True):
        """
        Returns the chi statistic such that P(T < t) = 1-alpha
        """

        temp = chi2(self.n).ppf(1-alpha)
        if verbose:
            print("For {} degrees of freedom,".format(self.n)) 
            print("P(Chi2 < {:.4f}) = {:.4f}".format(temp, 1-alpha))
        return temp

    def __str__(self) -> str:
        return "{} ~ Chi^2({})".format(self.name, self.n)

class F(Distribution):
    """
    Returns a random variable following an F distribution with degrees of freedom n1 and n2.
    i.e. F ~ F(n1, n2)

    n1: degrees of freedom for first sample
    n2: degrees of freedom for second sample

    """

    def __init__(self, n1, n2, name="X"):
        super().__init__(name, f(n1, n2))
        self.n1 = n1
        self.n2 = n2
        print("{} ~ F({:.4f}, {:.4f})".format(name, n1, n2))
    
    def statistic(S1, S2, sigma1, sigma2):
        temp =  (S1**2 / sigma1**2) / (S2**2 / sigma2**2)
        if verbose:
            print("F = ({}^2 / {}^2) / ({}^2 / {}^2) = {:.4f},".format(S1, sigma1, S2, sigma2, temp))
        return temp
    
    def interval(self, s1, s2, confidence):
        alpha = 1 - confidence
        f_lower = self.inverse(confidence + alpha / 2)
        f_upper = self.inverse(alpha / 2)

        ratio = s1**2 / s2**2
        temp = (ratio * (1 / f_lower), ratio * (1 / f_upper))

        if verbose:
            print("The {}% CI for the ratio of variances based on {} is {}".format(confidence*100, self.name, temp))
        return temp

    def inverse(self, alpha, verbose=True):
        """
        Returns the f statistic such that P(T < t) = 1-alpha
        """

        temp = f(self.n1, self.n2).ppf(1-alpha)
        if verbose:
            print("For ({}, {}) degrees of freedom,".format(self.n1, self.n2)) 
            print("P(F < {:.4f}) = {:.4f}".format(temp, 1-alpha))
        return temp

    def __str__(self) -> str:
        return "{} ~ Chi^2({})".format(self.name, self.n)

if __name__ == "__main__":
    x = Binomial(2, 1, "X")
    print(x)
    x.pdf(2, verbose = True)

    y = Normal(24, 3.8, "Y")
    print(y)
    print(1-y.leqCDF(30))

    z = NegativeBinomial(2, 0.5, "Z")
    print(z)
    z.pdf(7, True)

    w = NegativeBinomial(1, 0.75, "W")
    w.cdf(3, True)

    p = Poisson(2, "A")
    print(1-p.leqCDF(3))
    p.pdf(0, True)

    t = T(9-1)

    print(1-t.leqCDF(T.of(24, 20, 4.1, 9), verbose=True))

    print(1- Normal().leqCDF(Normal.of(0.2, 0, (1/18)**0.5, 36)))

    print(1- Normal(0, (1/18)**0.5).leqCDF(0.2))

    
