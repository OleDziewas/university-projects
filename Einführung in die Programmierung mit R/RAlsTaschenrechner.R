rnorm(3)
rnorm(3, 100, 5)
rnorm(mean = 2, sd = 3, n = 4)

a <- 1
a

"<-"(b,2)
b

2 + 3
5 - 8
2 * 8
10 / 5
4 ^ 3
2 * (3 + 5)
2 * ((3.2 + 5) / 4)
2 * {{3 + 5} / 4.1}
sqrt(64)
sum(c(1, 2, 3, 4, 5))

x <- rnorm(100, 10 ,20)
mean(x)
var(x)
sd(x)
cor(x, x)
cov(x, x)
IQR(x)
quantile(x)

cumsum(1:4)
probs <- c(0.2, 0.1, 0.4, 0.2, 0.1)
sum(probs)
cumsum(probs)
diff(1:10)
diff(c(1, 3, 6, 10), 1)
diff(c(1, 3, 6, 10), 1, 2)

(uhr12 <- 17 %% 12)
13 %% 2
prod(1:4)
cumprod(1:4)

sort(c(3, 1, 2))
sort(c(3, 1, 2), decreasing = TRUE)
order(c(3, 1, 2))
