from captcha.image import ImageCaptcha
n="16227"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')