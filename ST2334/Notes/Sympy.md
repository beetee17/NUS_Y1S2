---
geometry: margin=1.5cm
---

::: {.cell .code execution_count="1"}
``` python
from sympy import *

x = Symbol('x')
y = Symbol('y')
```
:::

::: {.cell .markdown}
### Qn: Suppose the joint density function of the random variables $X$ and $Y$ are as follows:

$$
f_{X,Y}(x,y)=   
\left\{
\begin{array}{ll}
      \frac{2}{3}(x+2y), & 0 \leq x \leq 1, 0, \leq y \leq 1 \\
      0, & \text{otherwise} \\
\end{array} 
\right.  
$$
:::

::: {.cell .code execution_count="2"}
``` python
f_xy = Rational(2, 3) * (x + 2*y)
```
:::

::: {.cell .markdown}
#### a) Find V(X)

$$f_X(x) = \int_0^1 f_{X,Y}(x, y)dy = \frac{2}{3} (x + 1)$$
:::

::: {.cell .code execution_count="3"}
``` python
f_x = integrate(f_xy, (y, 0, 1))
```
:::

::: {.cell .markdown}
$$E(X) = \int_0^1 x f_{X}(x)dx = \frac{5}{9}$$
$$E(X^2) = \int_0^1 x^2 f_{X}(x)dx = \frac{7}{18}$$
$$V(X) = E(X^2) - E(X)^2 =\frac{13}{162}$$
:::

::: {.cell .code execution_count="4"}
``` python
E_X = integrate(x * f_x, (x, 0, 1))
E_X2 = integrate(x**2 * f_x, (x, 0, 1))
V_X = E_X2 - E_X**2
```
:::

::: {.cell .markdown}
#### b) Find V(Y)
:::

::: {.cell .code execution_count="5"}
``` python
f_y = integrate(f_xy, (x, 0, 1))
```
:::

::: {.cell .code execution_count="6"}
``` python
E_Y = integrate(y * f_y, (y, 0, 1))
E_Y2 = integrate(y**2 * f_y, (y, 0, 1))
V_Y = E_Y2 - E_Y**2
print(E_Y, E_Y2, V_Y)
```

::: {.output .stream .stdout}
    11/18 4/9 23/324
:::
:::

::: {.cell .markdown}
#### c) Find Cov(X, Y)
:::

::: {.cell .markdown}
$$Cov(X, Y) = E( (X - E(X))(Y - E(Y)) )
            = \int_0^1 \int_0^1 (x - E(X)) (y - E(Y)) f_{X, Y}(x, y) dx dy = -\frac{1}{162}$$
:::

::: {.cell .code execution_count="7"}
``` python
Cov_XY = integrate( (x - E_X) * (y - E_Y) * f_xy, (x, 0, 1), (y, 0, 1))
```
:::

\pagebreak
::: {.cell .markdown}
### Qn: Suppose the joint density function of the random variables $X$ and $Y$ are as follows: 

$$
f_{X,Y}(x,y)=   
\left\{
\begin{array}{ll}
      k(x^2+y^2), & 3 \leq x \leq 5, \quad 3 \leq y \leq 5 \\
      0, & \text{otherwise} \\
\end{array} 
\right.  
$$
:::

::: {.cell .markdown}
#### a) Find $k$
:::

::: {.cell .markdown}
$$\int_3^5 \int_3^5 f_{X,Y}(x, y)dx dy = \frac{392k}{3}$$
:::

::: {.cell .code execution_count="15"}
``` python
f_xy = Symbol('k') * (x**2 + y**2)
integrate(f_xy, (x, 3, 5), (y, 3, 5))
```

::: {.output .execute_result execution_count="15"}
```{=latex}
$\displaystyle \frac{392 k}{3}$
```
:::
:::

::: {.cell .markdown}
$$ \frac{392k}{3} = 1 \rightarrow k = \frac{3}{392}$$
:::

::: {.cell .code execution_count="16"}
``` python
k = Rational(3, 392)
```
:::

::: {.cell .markdown}
#### b) Find $Pr(3 \leq X \leq 4 \text{ and } 4 \leq Y < 5)$ {#b-find-pr3-leq-x-leq-4-text-and--4-leq-y--5}

$$Pr(3 \leq X \leq 4 \text{ and } 4 \leq Y < 5) = \int_4^5 \int_3^4 f_{X, Y}(x, y) dx dy = \frac{1}{4}$$
:::

::: {.cell .code execution_count="17"}
``` python
f_xy = k * (x**2 + y**2)
integrate(f_xy, (x, 3, 4), (y, 4, 5))
```

::: {.output .execute_result execution_count="17"}
```{=latex}
$\displaystyle \frac{1}{4}$
```
:::
:::

::: {.cell .markdown}
#### c) Find $Pr(3.5 < X < 4)$ {#c-find-pr35--x--4}

$$f_X(x) = \int_3^5 f_{X,Y}(x, y)dy = \frac{3}{196}x^2 + \frac{1}{4}$$
:::

::: {.cell .code execution_count="18"}
``` python
f_x = integrate(f_xy, (y, 3, 5))
```
:::

::: {.cell .markdown}
$$Pr(3.5 < X < 4) = \int_{3.5}^4 f_X(x) dx = 0.2328$$
:::

::: {.cell .code execution_count="19"}
``` python
integrate(f_x, (x, 3.5, 4))
```

::: {.output .execute_result execution_count="19"}
```{=latex}
$\displaystyle 0.232780612244898$
```
:::
:::
