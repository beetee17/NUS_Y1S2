from scipy.stats import binom, nbinom, poisson, expon, norm

class Distribution:
    def __init__(self, name, distribution):
        self.name = name
        self.distribution = distribution

    def pdf(self, x, verbose=False):
        temp = self.distribution.pmf(x)
        if verbose: print("P(X = {}) = {}\n".format(x, temp))
        return temp

    def cdf(self, x, verbose=False):
        temp = self.distribution.cdf(x)
        if verbose: print("P(X <= {}) = {}\n".format(x, temp))
        return temp

    def mean(self, verbose=False):
        temp = self.distribution.mean(x)
        if verbose: print("E(X) = {}\n".format(temp))
        return temp

    def variance(self, verbose=False):
        temp = self.distribution.var(x)
        if verbose: print("Var(X) = {}".format(temp))
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

    def pdf(self, x, verbose=False):
        temp = super().pdf(x - self.k, verbose=False)
        if verbose: print("P(X = {}) = {}\n".format(x, temp))
        return temp

    def cdf(self, x, verbose=False):
        temp = super().cdf(x - self.k, verbose=False)
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

    def __str__(self) -> str:
        return "{} ~ N({}, {}^2)".format(self.name, self.mu, self.sigma)

if __name__ == "__main__":
    x = Binomial(2, 1, "X")
    print(x)
    x.pdf(2, verbose = True)

    y = Normal(24, 3.8, "Y")
    print(y)
    print(1-y.cdf(30))

    z = NegativeBinomial(2, 0.5, "Z")
    print(z)
    z.pdf(7, True)

    w = NegativeBinomial(1, 0.75, "W")
    w.cdf(3, True)

    p = Poisson(2, "A")
    print(1-p.cdf(3))
    p.pdf(0, True)