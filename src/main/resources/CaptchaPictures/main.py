from captcha.image import ImageCaptcha
n="14643"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')